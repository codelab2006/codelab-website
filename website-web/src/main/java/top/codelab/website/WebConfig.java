package top.codelab.website;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.*;
import top.codelab.website.web.exception.PathNotFoundException;
import top.codelab.website.web.interceptor.HandlerInterceptor;
import top.codelab.website.web.interceptor.WebRequestInterceptor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@EnableWebMvc
@ComponentScan({"top.codelab.website.web"})
@PropertySource("file:${CODELAB_WEBSITE_CONFIG}")
@ImportResource({"classpath:web-config.xml"})
class WebConfig implements WebMvcConfigurer {

    private static final Logger logger = LogManager.getLogger();

    private final Path resourcePath;

    WebConfig(@Value("${RES_PATH:#{null}}") String resourcePath) {
        this.resourcePath = Paths.get(this.appendTrailingSlash(resourcePath)).toAbsolutePath();
        if (!Files.exists(this.resourcePath)) {
            throw new PathNotFoundException(this.resourcePath.toString());
        }
        logger.info("Web resource path: {}", this.resourcePath);
    }

    private String appendTrailingSlash(String s) {
        return s.endsWith("/") ? s : s.concat("/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor());
        registry.addWebRequestInterceptor(new WebRequestInterceptor());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations(this.resourcePath.toUri().toString());
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
