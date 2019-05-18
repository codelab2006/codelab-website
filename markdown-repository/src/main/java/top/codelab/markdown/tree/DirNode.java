package top.codelab.markdown.tree;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;

public class DirNode extends TreeNode {

    private final Set<TreeNode> children = new HashSet<>();

    DirNode(Path path, BasicFileAttributes attrs) {
        super(path, attrs);
    }

    @Override
    Set<TreeNode> getChildren() {
        return this.children;
    }
}
