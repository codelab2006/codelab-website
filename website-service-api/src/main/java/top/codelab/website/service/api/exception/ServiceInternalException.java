package top.codelab.website.service.api.exception;

import top.codelab.common.exception.InternalException;

class ServiceInternalException extends InternalException implements ServiceException {

    ServiceInternalException(String message) {
        super(message);
    }
}
