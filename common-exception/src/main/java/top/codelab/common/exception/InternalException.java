package top.codelab.common.exception;

public class InternalException extends RuntimeException {

    public InternalException(String message) {
        super(message);
    }

    public InternalException(Throwable cause) {
        super(cause);
    }
}
