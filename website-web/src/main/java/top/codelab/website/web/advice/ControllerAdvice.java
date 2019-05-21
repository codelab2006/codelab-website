package top.codelab.website.web.advice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import top.codelab.common.exception.NotFoundException;
import top.codelab.website.web.filter.MDCFilter;

@org.springframework.web.bind.annotation.ControllerAdvice({"top.codelab.website.web.controller"})
class ControllerAdvice {

    private static final String VIEW_404 = "404";
    private static final String VIEW_500 = "500";

    private static final Logger logger = LogManager.getLogger();

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String handleNotFound(NotFoundException e) {
        logger.warn(e);
        return VIEW_404;
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ModelAndView handleInternalServerError(Throwable throwable) {
        logger.error(throwable);
        ModelAndView modelAndView = new ModelAndView(VIEW_500);
        modelAndView.addObject(MDCFilter.MDC_UUID, ThreadContext.get(MDCFilter.MDC_UUID));
        return modelAndView;
    }
}
