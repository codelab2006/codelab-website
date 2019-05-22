package top.codelab.website.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import top.codelab.markdown.MarkdownRepository;

@Configuration
@PropertySource("file:${CODELAB_MARKDOWN_DATA_SOURCE_CONFIG}")
class ServiceConfig {

    @Bean
    public MarkdownRepository markdownRepository(@Value("${MARKDOWN_DOCUMENT_PATH:#{null}}") String documentPath,
                                                 @Value("${MARKDOWN_RESOURCE_PATH:#{null}}") String resourcePath) {
        return new MarkdownRepository(documentPath, resourcePath);
    }
}
