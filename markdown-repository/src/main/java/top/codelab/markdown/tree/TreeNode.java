package top.codelab.markdown.tree;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;

public abstract class TreeNode {

    private final Path path;
    private final BasicFileAttributes attrs;

    TreeNode(Path path, BasicFileAttributes attrs) {
        this.path = path;
        this.attrs = attrs;
    }

    /**
     * 添加一个子节点
     *
     * @param node 子节点
     * @return 子节点是否添加成功
     */
    boolean appendNode(TreeNode node) {
        if (!this.isDir()) return false;
        if (node.path.getParent().equals(this.path)) return this.getChildren().add(node);
        return this.getChildren().stream().anyMatch(treeNode -> treeNode.appendNode(node));
    }

    boolean isDir() {
        return this.attrs.isDirectory();
    }

    abstract Set<TreeNode> getChildren();

    /**
     * 返回节点名称
     *
     * @return 节点名称
     */
    public String getName() {
        return this.path.getFileName().toString();
    }

    /**
     * 返回节点路径
     *
     * @return 节点路径
     */
    public Path getPath() {
        return this.path;
    }

    public LocalDateTime getCreationTime() {
        return this.toLocalDateTime(this.attrs.creationTime());
    }

    public LocalDateTime getLastModifiedTime() {
        return this.toLocalDateTime(this.attrs.lastModifiedTime());
    }

    /**
     * 将 FileTime 转为 LocalDateTime
     */
    private LocalDateTime toLocalDateTime(FileTime input) {
        return input
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
