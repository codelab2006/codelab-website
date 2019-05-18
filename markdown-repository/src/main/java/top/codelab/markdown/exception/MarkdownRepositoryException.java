package top.codelab.markdown.exception;

public class MarkdownRepositoryException extends RuntimeException {

    MarkdownRepositoryException(String message) {
        super(message);
    }

    public MarkdownRepositoryException(Throwable cause) {
        super(cause);
    }
}
