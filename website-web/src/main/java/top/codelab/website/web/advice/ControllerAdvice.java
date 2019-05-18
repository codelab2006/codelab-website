package top.codelab.website.web.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import top.codelab.website.web.exception.NotFoundException;

@org.springframework.web.bind.annotation.ControllerAdvice({"top.codelab.website.web.controller"})
class ControllerAdvice {

    private static final String VIEW_404 = "404";
    private static final String VIEW_500 = "500";

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String handleNotFound() {
        return VIEW_404;
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String handleInternalServerError() {
        return VIEW_500;
    }
}
