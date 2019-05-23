package top.codelab.markdown;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.codelab.markdown.exception.DataNotFoundException;
import top.codelab.markdown.exception.LimitExceededException;
import top.codelab.markdown.exception.PathNotFoundException;
import top.codelab.markdown.tree.FileNode;
import top.codelab.markdown.tree.FileTree;
import top.codelab.markdown.tree.FileTreeGenerator;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class MarkdownRepository {

    private static final int ONE = 1;
    private static final String SLASH = "/";
    private static final String KEY_DOCUMENT_TREE = "DOCUMENT_TREE";
    private static final String KEY_PREFIX_FILE_TREE = "file_tree_";
    private static final String KEY_PREFIX_MARKDOWN_FILES = "markdown_files_in_";

    private static final Logger logger = LogManager.getLogger();

    private final Path documentPath;
    private final Path resourcePath;

    private final Cache<String, Object> cache;

    public MarkdownRepository(String documentPath, String resourcePath) {
        this.documentPath = this.getAbsolutePath(documentPath);
        logger.info("Markdown repository document path: {}", this.documentPath);
        this.resourcePath = this.getAbsolutePath(resourcePath);
        logger.info("Markdown repository resource path: {}", this.resourcePath);
        this.cache = new Cache<>();
        this.refreshDocumentTree();
    }

    private Path getDocumentPath() {
        return this.documentPath;
    }

    private Path getResourcePath() {
        return this.resourcePath;
    }

    private String appendTrailingSlash(String s) {
        return s.endsWith(SLASH) ? s : s.concat(SLASH);
    }

    private Path getAbsolutePath(String p) {
        Path path = Paths.get(this.appendTrailingSlash(p)).toAbsolutePath();
        if (!Files.exists(path)) {
            throw new PathNotFoundException(path.toString());
        }
        return path;
    }

    public void refreshDocumentTree() {
        this.cache.clear().add(KEY_DOCUMENT_TREE, this.generateDocumentTree());
    }

    private FileTree generateDocumentTree() {
        return FileTreeGenerator.generate(this.getDocumentPath());
    }

    private Set<FileTree> findFileTrees(String treeName) {
        return ((FileTree) this.cache.get(KEY_DOCUMENT_TREE)).findDirNodes().stream()
                .filter(node -> node.getName().equals(treeName))
                .map(FileTree::new)
                .collect(Collectors.toSet());
    }

    private FileTree findFileTree(String treeName) {
        String key = KEY_PREFIX_FILE_TREE.concat(treeName);
        return (FileTree) Optional.ofNullable(this.cache.get(key)).orElseGet(() -> {
            Set<FileTree> fileTrees = this.findFileTrees(treeName);
            if (fileTrees.isEmpty())
                throw new DataNotFoundException(String.format("Directory not found: %s", treeName));
            if (fileTrees.size() > ONE) {
                throw new LimitExceededException(String.format("The result contains more than one directory, limited: %d, actual: %d", ONE, fileTrees.size()));
            }
            FileTree fileTree = fileTrees.iterator().next();
            this.cache.add(key, fileTree);
            return fileTree;
        });
    }

    public List<MarkdownFile> findMarkdownFiles(String directoryName) {
        String key = KEY_PREFIX_MARKDOWN_FILES.concat(directoryName);
        List<MarkdownFile> value = Util.cast(this.cache.get(key));
        return Optional.ofNullable(value).orElseGet(() -> {
            List<MarkdownFile> markdownFiles = this.findFileTree(directoryName).findFileNodes().stream()
                    .filter(fileNode -> FilenameUtils.isExtension(fileNode.getName(), "md"))
                    .sorted(Comparator.comparing(FileNode::getLastModifiedTime).reversed())
                    .map(MarkdownFile::new)
                    .collect(Collectors.toList());
            this.cache.add(key, markdownFiles);
            return markdownFiles;
        });
    }

    public MarkdownFile findMarkdownFileByDirectoryAndName(String directoryName, String name) {
        List<MarkdownFile> markdownFiles = this.findMarkdownFiles(directoryName).stream()
                .filter(markdownFile -> FilenameUtils.removeExtension(markdownFile.getName()).equals(name))
                .collect(Collectors.toList());
        if (markdownFiles.isEmpty()) {
            throw new DataNotFoundException(String.format("Markdown file not found: %s/%s", directoryName, name));
        }
        if (markdownFiles.size() > ONE) {
            throw new LimitExceededException(String.format("The result contains more than one markdown file, limited: %d, actual: %d", ONE, markdownFiles.size()));
        }
        return markdownFiles.iterator().next();
    }
}
