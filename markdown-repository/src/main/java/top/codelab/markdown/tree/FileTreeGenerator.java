package top.codelab.markdown.tree;

import top.codelab.markdown.exception.MarkdownRepositoryException;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class FileTreeGenerator {

    public static FileTree generate(Path start) {
        FileTree fileTree = new FileTree();
        try {
            Files.walkFileTree(start, new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    fileTree.appendNode(new DirNode(dir, attrs));
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    fileTree.appendNode(new FileNode(file, attrs));
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new MarkdownRepositoryException(e);
        }
        return fileTree;
    }
}
