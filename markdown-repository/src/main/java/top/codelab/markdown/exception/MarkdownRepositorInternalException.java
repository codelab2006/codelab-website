package top.codelab.markdown.exception;

import top.codelab.common.exception.InternalException;

public class MarkdownRepositorInternalException extends InternalException implements MarkdownRepositoryException {

    MarkdownRepositorInternalException(String message) {
        super(message);
    }

    public MarkdownRepositorInternalException(Throwable cause) {
        super(cause);
    }
}
