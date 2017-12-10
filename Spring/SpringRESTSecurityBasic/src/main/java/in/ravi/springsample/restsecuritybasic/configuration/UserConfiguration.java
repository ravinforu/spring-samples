package in.ravi.springsample.restsecuritybasic.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "in.ravi.springsample.restsecuritybasic")
public class UserConfiguration {
}
