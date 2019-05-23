package top.codelab.website.web.exception;

import top.codelab.common.exception.NotFoundException;

public class PathNotFoundException extends NotFoundException implements WebException {

    public PathNotFoundException(String message) {
        super(message);
    }
}
