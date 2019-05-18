package top.codelab.markdown.tree;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class FileTree {

    private TreeNode rootNode;

    FileTree() {
    }

    public FileTree(TreeNode rootNode) {
        this.appendNode(rootNode);
    }

    /**
     * 添加一个子节点
     *
     * @param node 子节点
     */
    void appendNode(TreeNode node) {
        if (Objects.isNull(this.rootNode)) {
            this.rootNode = node;
        }
        this.rootNode.appendNode(node);
    }

    /**
     * 返回树的所有节点
     *
     * @return 所有节点的集合
     */
    private Set<TreeNode> findTreeNodes() {
        return this.findTreeNodes(this.rootNode);
    }

    /**
     * 返回初始节点及其所有子节点
     *
     * @param treeNode 初始节点
     * @return 所有节点的集合
     */
    private Set<TreeNode> findTreeNodes(TreeNode treeNode) {
        Set<TreeNode> treeNodes = new HashSet<>();
        Optional.ofNullable(treeNode).ifPresent(node -> {
            treeNodes.add(node);
            node.getChildren().forEach(n -> treeNodes.addAll(this.findTreeNodes(n)));
        });
        return treeNodes;
    }

    public Set<DirNode> findDirNodes() {
        return this.findTreeNodes().stream()
                .filter(TreeNode::isDir)
                .map(treeNode -> (DirNode) treeNode).collect(Collectors.toSet());
    }

    public Set<FileNode> findFileNodes() {
        return this.findTreeNodes().stream()
                .filter(treeNode -> !treeNode.isDir())
                .map(treeNode -> (FileNode) treeNode).collect(Collectors.toSet());
    }
}
