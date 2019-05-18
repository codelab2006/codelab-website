package top.codelab.website.service.api.exception;

class ServiceException extends RuntimeException {

    ServiceException(String message) {
        super(message);
    }
}
