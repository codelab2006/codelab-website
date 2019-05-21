package top.codelab.markdown.exception;

import top.codelab.common.exception.InternalServerException;

public class LimitExceededException extends InternalServerException implements MarkdownRepositoryException {

    public LimitExceededException(String message) {
        super(message);
    }
}
