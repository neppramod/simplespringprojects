package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import proxy.LoggingAspect;

@Configuration
@ComponentScan(basePackages = "services")
@EnableAspectJAutoProxy
public class ProjectConfig {

    // Add LoggingAspect instance to spring context
    @Bean
    public LoggingAspect aspect() {
        return new LoggingAspect();
    }
}
