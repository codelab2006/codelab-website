package top.codelab.markdown.tree;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class FileTreeTest {

    private static final String DOCUMENTS = "documents";
    private static Path path;

    @BeforeClass
    public static void setup() throws Exception {
        path = Paths.get(Objects.requireNonNull(FileTreeTest.class.getClassLoader().getResource(DOCUMENTS)).toURI());
    }

    @Test
    public void testFindNodes() {
        FileTree fileTree = FileTreeGenerator.generate(path);
        Assert.assertEquals(23, fileTree.findDirNodes().size());
        Assert.assertEquals(28, fileTree.findFileNodes().size());
    }
}
