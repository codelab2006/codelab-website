package top.codelab.website;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.*;
import top.codelab.website.web.interceptor.HandlerInterceptor;
import top.codelab.website.web.interceptor.WebRequestInterceptor;

@Configuration
@EnableWebMvc
@ComponentScan({"top.codelab.website.web"})
@ImportResource({"classpath:web-config.xml"})
class WebConfig implements WebMvcConfigurer {

    private final String resourcePath;

    private String appendTrailingSlash(String s) {
        return s.endsWith("/") ? s : s.concat("/");
    }

    WebConfig(@Value("${RES_PATH:#{null}}") String resourcePath) {
        this.resourcePath = this.appendTrailingSlash(resourcePath);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor());
        registry.addWebRequestInterceptor(new WebRequestInterceptor());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("file:".concat(this.resourcePath));
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
