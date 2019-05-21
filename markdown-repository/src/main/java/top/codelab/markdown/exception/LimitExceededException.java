package top.codelab.markdown.exception;

public class LimitExceededException extends MarkdownRepositorInternalException {

    public LimitExceededException(String message) {
        super(message);
    }
}
