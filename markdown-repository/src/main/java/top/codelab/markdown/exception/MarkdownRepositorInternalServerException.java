package top.codelab.markdown.exception;

import top.codelab.common.exception.InternalServerException;

public class MarkdownRepositorInternalServerException extends InternalServerException implements MarkdownRepositoryException {

    public MarkdownRepositorInternalServerException(Throwable cause) {
        super(cause);
    }
}
