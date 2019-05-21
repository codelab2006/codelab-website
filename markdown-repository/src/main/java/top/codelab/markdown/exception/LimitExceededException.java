package top.codelab.markdown.exception;

import top.codelab.common.exception.InternalException;

public class LimitExceededException extends InternalException implements MarkdownRepositoryException {

    public LimitExceededException(String message) {
        super(message);
    }
}
