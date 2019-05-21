package top.codelab.markdown.exception;

import top.codelab.common.exception.NotFoundException;

public class DataNotFoundException extends NotFoundException implements MarkdownRepositoryException {

    public DataNotFoundException(String message) {
        super(message);
    }
}
