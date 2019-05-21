package top.codelab.website.web.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.AsyncWebRequestInterceptor;
import org.springframework.web.context.request.WebRequest;

public class WebRequestInterceptor implements AsyncWebRequestInterceptor {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void preHandle(WebRequest request) {
    }

    @Override
    public void postHandle(WebRequest request, ModelMap model) {
    }

    @Override
    public void afterCompletion(WebRequest request, Exception ex) {
    }

    @Override
    public void afterConcurrentHandlingStarted(WebRequest request) {
    }
}
