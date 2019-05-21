package top.codelab.markdown.exception;

import top.codelab.common.exception.NotFoundException;

public class PathNotFoundException extends NotFoundException implements MarkdownRepositoryException {

    public PathNotFoundException(String message) {
        super(message);
    }
}
