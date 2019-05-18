package top.codelab.markdown.exception;

public class LimitExceededException extends MarkdownRepositoryException {

    public LimitExceededException(String message) {
        super(message);
    }
}
