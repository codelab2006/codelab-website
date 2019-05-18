package top.codelab.markdown;

import org.junit.*;
import org.junit.rules.ExpectedException;
import top.codelab.markdown.exception.DataNotFoundException;
import top.codelab.markdown.exception.LimitExceededException;
import top.codelab.markdown.exception.PathNotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class MarkdownRepositoryTest {

    private static final String MARKDOWN_DATA_SOURCE_CONFIG = "codelab-markdown-data-source.properties";
    private static final String MARKDOWN_DOC_PATH = "MARKDOWN_DOC_PATH";
    private static final String MARKDOWN_RES_PATH = "MARKDOWN_RES_PATH";
    private static final String UNKNOWN_MARKDOWN_DOC_PATH = "unknown_markdown_doc_path";
    private static final String UNKNOWN_MARKDOWN_RES_PATH = "unknown_markdown_res_path";

    private static Properties properties;

    private MarkdownRepository markdownRepository;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void setup() throws Exception {
        ClassLoader classLoader = MarkdownRepositoryTest.class.getClassLoader();
        properties = new Properties();
        properties.load(Objects.requireNonNull(classLoader.getResourceAsStream(MARKDOWN_DATA_SOURCE_CONFIG)));
    }

    private String getMarkdownDocPath() {
        return properties.getProperty(MARKDOWN_DOC_PATH);
    }

    private String getMarkdownResPath() {
        return properties.getProperty(MARKDOWN_RES_PATH);
    }

    @Before
    public void init() {
        this.markdownRepository = new MarkdownRepository(this.getMarkdownDocPath(), this.getMarkdownResPath());
    }

    @Test
    public void throwsPathNotFoundExceptionForDocPath() {
        this.exception.expect(PathNotFoundException.class);
        this.exception.expectMessage(UNKNOWN_MARKDOWN_DOC_PATH);
        new MarkdownRepository(UNKNOWN_MARKDOWN_DOC_PATH, this.getMarkdownResPath());
    }

    @Test
    public void throwsPathNotFoundExceptionForResPath() {
        this.exception.expect(PathNotFoundException.class);
        this.exception.expectMessage(UNKNOWN_MARKDOWN_RES_PATH);
        new MarkdownRepository(this.getMarkdownDocPath(), UNKNOWN_MARKDOWN_RES_PATH);
    }

    @Test
    public void throwsDataNotFoundExceptionForFindMarkdownFiles() {
        this.exception.expect(DataNotFoundException.class);
        this.exception.expectMessage("Directory not found: directory");
        this.markdownRepository.findMarkdownFiles("directory");
    }

    @Test
    public void throwsLimitExceededExceptionForFindMarkdownFiles() {
        this.exception.expect(LimitExceededException.class);
        this.exception.expectMessage("The result contains more than one directory, limited: 1, actual: 3");
        this.markdownRepository.findMarkdownFiles("o");
    }

    @Test
    public void testFindMarkdownFiles() {
        List<MarkdownFile> markdownFiles = this.markdownRepository.findMarkdownFiles("blog");
        Assert.assertNotNull(markdownFiles);
        Assert.assertEquals(13, markdownFiles.size());
    }

    @Test
    public void throwsDataNotFoundExceptionForFindMarkdownFileByDirectoryAndName() {
        this.exception.expect(DataNotFoundException.class);
        this.exception.expectMessage("Markdown file not found: blog/0");
        this.markdownRepository.findMarkdownFileByDirectoryAndName("blog", "0");
    }

    @Test
    public void throwsLimitExceededExceptionForFindMarkdownFileByDirectoryAndName() {
        this.exception.expect(LimitExceededException.class);
        this.exception.expectMessage("The result contains more than one markdown file, limited: 1, actual: 3");
        this.markdownRepository.findMarkdownFileByDirectoryAndName("blog", "00");
    }

    @Test
    public void testFindMarkdownFileByDirectoryAndName() {
        MarkdownFile markdownFiles = this.markdownRepository.findMarkdownFileByDirectoryAndName("blog", "10");
        Assert.assertNotNull(markdownFiles);
    }
}
