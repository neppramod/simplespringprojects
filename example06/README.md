# Example 06

This project covers Chapter 6 (second part) of Aspected Oriented Programming.

Look at Example 05 to get most of the logic.

Here, we intercept the joinPoint, pass altered object to execute in our target method, and also return a different value to the caller.

Other than returning "SUCCESS" from CommentService we don't change other classes that much, except `LoggingAspect`

```Java
package proxy;

import model.Comment;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.Arrays;
import java.util.logging.Logger;

@Aspect
public class LoggingAspect {

    private Logger logger = Logger.getLogger(LoggingAspect.class.getName());

    @Around("execution(* services.*.*(..))")  // syntax is <when_method_is_called>(<any_return_type> <service package>.*<any_class>.*<any_method_name>(..<any_parameter>))
    public String log(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();

        logger.info("Method " + methodName + " with parameters " + Arrays.asList(arguments) + " will execute");
        logger.info("But altered");

        // Alter the input parameter and pass it to the intercepted method
        Comment comment = new Comment();
        comment.setText("Some other text!");

        Object [] newArguments = {comment};

        // If we don't alter the comment, we could just call jointPoint.proceed();
        Object returnedByMethod = joinPoint.proceed(newArguments);  // If that method returns value (e.g. Let's return SUCCESS from sendComment() method)

        logger.info("Method executed and returned " + joinPoint);
        return "FAILED";  // Although target method returned SUCCESS
    }
}
```

Output
```
Sep 09, 1993 3:55:51 PM proxy.LoggingAspect log
INFO: Method publishComment with parameters [model.Comment@6f8e8894] will execute
Sep 09, 1993 3:55:51 PM proxy.LoggingAspect log
INFO: But altered
Sep 09, 1993 3:55:51 PM services.CommentService publishComment
INFO: Publishing comment: Some other text!
Sep 09, 1993 3:55:51 PM proxy.LoggingAspect log
INFO: Method executed and returned execution(String services.CommentService.publishComment(Comment))
Sep 09, 1993 3:55:51 PM main.Main main
INFO: FAILED
```