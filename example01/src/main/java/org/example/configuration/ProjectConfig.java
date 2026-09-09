package org.example.configuration;

import org.example.model.Parrot;
import org.example.model.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.example.model")
public class ProjectConfig {
}
