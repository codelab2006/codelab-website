package top.codelab.website;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"top.codelab.website.service"})
class RootConfig {
}
