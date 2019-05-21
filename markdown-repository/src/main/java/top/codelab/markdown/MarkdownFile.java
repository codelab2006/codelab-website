package top.codelab.markdown;

import top.codelab.markdown.exception.MarkdownRepositorInternalException;
import top.codelab.markdown.tree.FileNode;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;

public class MarkdownFile {

    private final FileNode fileNode;
    private final List<String> lines;

    MarkdownFile(FileNode fileNode) {
        try {
            this.fileNode = fileNode;
            this.lines = Files.readAllLines(this.fileNode.getPath());
        } catch (IOException e) {
            throw new MarkdownRepositorInternalException(e);
        }
    }

    public String getName() {
        return this.fileNode.getName();
    }

    public List<String> getLines() {
        return this.lines;
    }

    public LocalDateTime getCreationTime() {
        return this.fileNode.getCreationTime();
    }

    public LocalDateTime getLastModifiedTime() {
        return this.fileNode.getLastModifiedTime();
    }
}
