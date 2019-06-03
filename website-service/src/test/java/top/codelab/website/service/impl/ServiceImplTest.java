package top.codelab.website.service.impl;

import org.apache.commons.lang3.RegExUtils;
import org.easymock.EasyMock;
import org.junit.*;
import org.junit.rules.ExpectedException;
import top.codelab.markdown.MarkdownFile;
import top.codelab.markdown.MarkdownRepository;
import top.codelab.website.service.api.exception.IllegalFormatException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ServiceImplTest {

    private static final String ExceptionMessageForExtractTitle = "Illegal post title: #exception-extract-title";
    private static final String IllegalPostTitle = "#exception-extract-title";
    private static final String PostTitleLine = "# Extract Title";
    private static final String PostTitle = "Extract Title";

    private static final String ExceptionMessageForExtractAuthor = "Illegal post author: >author: exception-extract-author ";
    private static final String IllegalPostAuthor = ">author: exception-extract-author ";
    private static final String PostAuthorLine = "> author: Yongjian Huang ";
    private static final String PostAuthor = "Yongjian Huang";

    private static final String ExceptionMessageForExtractTags = "Illegal post tags: >tags: exception-extract-tags ";
    private static final String IllegalPostTags = ">tags: exception-extract-tags ";
    private static final String PostTagsLine = "> tags: javascript,  nodejs,   java,    database";
    private static final String[] PostTags = new String[]{"javascript", "nodejs", "java", "database"};

    private static final String ExceptionMessageForExtractSummary = "Illegal post summary: separator not found in ";

    private static final String PostContentLine1 = "Hello world";
    private static final String PostContentLine2 = "Hello java";

    private static ServiceImpl serviceImpl;

    private MarkdownFile markdownFile;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void setup() {
        serviceImpl = new ServiceImpl(EasyMock.mock(MarkdownRepository.class));
    }

    @Before
    public void Before() {
        this.markdownFile = EasyMock.mock(MarkdownFile.class);
    }

    @Test
    public void throwsIllegalExceptionForExtractTitle() {
        this.exception.expect(IllegalFormatException.class);
        this.exception.expectMessage(ExceptionMessageForExtractTitle);
        EasyMock.expect(this.markdownFile.getLines())
                .andReturn(Collections.singletonList(IllegalPostTitle));
        EasyMock.replay(this.markdownFile);
        serviceImpl.extractTitle(this.markdownFile);
    }

    @Test
    public void testExtractTitle() {
        EasyMock.expect(this.markdownFile.getLines())
                .andReturn(Collections.singletonList(PostTitleLine));
        EasyMock.replay(this.markdownFile);
        Assert.assertEquals(PostTitle, serviceImpl.extractTitle(this.markdownFile));
    }

    @Test
    public void throwsIllegalExceptionForExtractAuthor() {
        this.exception.expect(IllegalFormatException.class);
        this.exception.expectMessage(ExceptionMessageForExtractAuthor);
        EasyMock.expect(this.markdownFile.getLines())
                .andReturn(Arrays.asList(PostTitleLine, IllegalPostAuthor))
                .times(2);
        EasyMock.replay(this.markdownFile);
        Assert.assertEquals(PostTitle, serviceImpl.extractTitle(this.markdownFile));
        serviceImpl.extractAuthor(this.markdownFile);
    }

    @Test
    public void testExtractAuthor() {
        EasyMock.expect(this.markdownFile.getLines())
                .andReturn(Arrays.asList(PostTitleLine, PostAuthorLine))
                .times(2);
        EasyMock.replay(this.markdownFile);
        Assert.assertEquals(PostTitle, serviceImpl.extractTitle(this.markdownFile));
        Assert.assertEquals(PostAuthor, serviceImpl.extractAuthor(this.markdownFile));
    }

    @Test
    public void throwsIllegalExceptionForExtractTags() {
        this.exception.expect(IllegalFormatException.class);
        this.exception.expectMessage(ExceptionMessageForExtractTags);
        EasyMock.expect(this.markdownFile.getLines())
                .andReturn(Arrays.asList(PostTitleLine, PostAuthorLine, IllegalPostTags))
                .times(3);
        EasyMock.replay(this.markdownFile);
        Assert.assertEquals(PostTitle, serviceImpl.extractTitle(this.markdownFile));
        Assert.assertEquals(PostAuthor, serviceImpl.extractAuthor(this.markdownFile));
        serviceImpl.extractTags(this.markdownFile);
    }

    @Test
    public void testExtractTags() {
        EasyMock.expect(this.markdownFile.getLines())
                .andReturn(Arrays.asList(PostTitleLine, PostAuthorLine, PostTagsLine))
                .times(3);
        EasyMock.replay(this.markdownFile);
        Assert.assertEquals(PostTitle, serviceImpl.extractTitle(this.markdownFile));
        Assert.assertEquals(PostAuthor, serviceImpl.extractAuthor(this.markdownFile));
        List<String> actual = serviceImpl.extractTags(this.markdownFile);
        IntStream.range(0, PostTags.length)
                .forEach(index -> Assert.assertEquals(PostTags[index], actual.get(index)));
    }

    @Test
    public void throwsIllegalExceptionForExtractSummary() {
        EasyMock.expect(this.markdownFile.getName())
                .andReturn("markdown_file")
                .times(2);
        EasyMock.expect(this.markdownFile.getLines())
                .andReturn(Arrays.asList(PostTitleLine, PostContentLine1));
        EasyMock.replay(this.markdownFile);
        this.exception.expect(IllegalFormatException.class);
        this.exception.expectMessage(ExceptionMessageForExtractSummary.concat(this.markdownFile.getName()));
        serviceImpl.extractSummary(this.markdownFile);
    }

    @Test
    public void testExtractSummary() {
        String expected = Arrays.stream(new String[]{PostContentLine1, PostContentLine2})
                .collect(Collectors.joining(System.lineSeparator()));
        EasyMock.expect(this.markdownFile.getLines())
                .andReturn(Arrays.asList(PostTitleLine, PostAuthorLine, PostTagsLine, "", PostContentLine1, PostContentLine2, "**********"));
        EasyMock.replay(this.markdownFile);
        Assert.assertEquals(expected, serviceImpl.extractSummary(this.markdownFile));
    }

    @Test
    public void testExtractSummaryHtml() {
        String expected = String.join("", new String[]{"<p>Hello world</p>", "<ol>", "  <li>a</li>", "  <li>b</li>", "</ol>"});
        EasyMock.expect(this.markdownFile.getLines())
                .andReturn(Arrays.asList(PostTitleLine, PostAuthorLine, PostTagsLine, "", PostContentLine1, "1. a", "2. b", "**********"));
        EasyMock.replay(this.markdownFile);
        String actual = serviceImpl.parseMarkdown(serviceImpl.extractSummary(this.markdownFile));
        Assert.assertTrue(actual.endsWith(">"));
        Assert.assertEquals(expected, RegExUtils.removeAll(actual, Pattern.compile("\\R")));
    }
}
