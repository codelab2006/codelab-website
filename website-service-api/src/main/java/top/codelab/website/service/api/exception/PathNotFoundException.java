package top.codelab.website.service.api.exception;

import top.codelab.common.exception.NotFoundException;

public class PathNotFoundException extends NotFoundException implements ServiceException {

    public PathNotFoundException(String message) {
        super(message);
    }
}
