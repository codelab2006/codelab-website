package top.codelab.markdown.tree;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.Set;

public class FileNode extends TreeNode {

    private final Set<TreeNode> children = Collections.emptySet();

    FileNode(Path path, BasicFileAttributes attrs) {
        super(path, attrs);
    }

    @Override
    Set<TreeNode> getChildren() {
        return this.children;
    }
}
