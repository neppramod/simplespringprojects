# Example 05

This project covers Chapter 6 (first part) of Aspected Oriented Programming.

Let's first define a barebone project without AspectJ

1. Create a `Comment` model class

```
package model;

public class Comment {
    private String text;
    private String author;
    
    // Getter/setter
}
```

2. Then create a `CommentService` class to publish comment

```
package services;

import model.Comment;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class CommentService {
    private Logger logger = Logger.getLogger(CommentService.class.getName());

    public void publishComment(Comment comment) {
        logger.info("Publishing comment: " + comment.getText());
    }
}
```

3. We need a ProjectConfig to scan the service class
```Java
package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "services")
public class ProjectConfig {
}
```

4. And finally use it from Main

```Java
package main;

import config.ProjectConfig;
import model.Comment;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import services.CommentService;

public class Main {
    void main() {
        var c = new AnnotationConfigApplicationContext(ProjectConfig.class);

        var service = c.getBean(CommentService.class);

        Comment comment = new Comment();
        comment.setText("I bought a Parrot");
        comment.setAuthor("Hari");

        service.publishComment(comment);
    }
}
```

5. Produces following output
```
Oct 02, 1995 2:17:28 PM services.CommentService publishComment
INFO: Publishing comment: I bought a Parrot
```

# Add AspectJ
We have not used any AspectJ capability to above code. To enable AspectJ to the project, we have to add the package to our pom.xml file

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>7.0.8</version>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-aspects</artifactId>
        <version>7.0.8</version>
    </dependency>
</dependencies>
```

Add `EnableAspectJAutoProxy` to the configuration class to enable it in the project. Next, we will create an Aspect class called `LoggingAspect`, 
and finally add it to the application context using `@Bean` (like before) through `ProjectConfig`

1. Add LoggingAspect
```Java
package proxy;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.logging.Logger;

@Aspect
public class LoggingAspect {

    private Logger logger = Logger.getLogger(LoggingAspect.class.getName());

    @Around("execution(* services.*.*(..))")  // syntax is <when_method_is_called>(<any_return_type> <service package>.*<any_class>.*<any_method_name>(..<any_parameter>))
    public void log(ProceedingJoinPoint joinPoint) {
        try {
            logger.info("Method will execute");
            joinPoint.proceed();  // call the point-cut method (actual method that was intercepted)
            logger.info("Method executed");
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }
}
```
2. Add `@EnableAspectJAutoProxy` annotation to `ProjectConfig` to add AspectJ and finally, need to add the `LoggingAspect` as a bean to spring context
```Java
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
```

Everything else remains same. This is the final output after adding AspectJ. 
As you can see the messages are executed before and after executing the pointcut. We use joinPoint.proceed() to forward the call to the pointcut method.

This aspect of calling the intercepted method through jointPoint allows us to even not call the method. 
This can be useful in a situation, say when you need to check if the user has a certain role, before executing the method. 
If the user does not have necessary credentials, you can return an object that represents error, rather than executing the actual method.

```
Oct 09, 2003 2:36:53 PM proxy.LoggingAspect log
INFO: Method will execute
Oct 09, 2003 2:36:53 PM services.CommentService publishComment
INFO: Publishing comment: I bought a Parrot
Oct 09, 2003 2:36:53 PM proxy.LoggingAspect log
INFO: Method executed
```