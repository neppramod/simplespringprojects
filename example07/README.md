# Example 07

# Intercepting annotated methods
We can use custom annotations to mark the methods we want an aspect to intercept, to avoid writing complex AspectJ pointcut expressions

- Define a custom annotation @ToLog (any name will do)
- Use a different AspectJ pointcut expression for the aspect method to tell aspect to intercept the methods annotated with the custom annotation. We need to set @Retention(RetentionPolicy.RUNTIME) so that the method can be intercepted at runtime. By default, in Java annotations cannot be intercepted at runtime. Also, a good idea to set language element so we can restrict where it can be used (method, class etc)

`ToLog` annotation

```Java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ToLog {
}
```

`Main` calls the two method in `CommentService`
```Java
service.publishComment(comment);
service.deleteComment(comment);
```

LoggingAspect is same as before, except we add the new pointcut annotation. One thing to be careful is that you need to match the package name for the annotation (here `annotation.ToLog`)
```Java
@Aspect
public class LoggingAspect {

    private Logger logger = Logger.getLogger(LoggingAspect.class.getName());

    @Around("@annotation(annotation.ToLog)")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Method will execute");
        Object result = joinPoint.proceed();  // call the point-cut method (actual method that was intercepted)
        logger.info("Method executed");
        return result;
    }
}
```

Finally, `CommentService`
```Java
@Service
public class CommentService {
    private Logger logger = Logger.getLogger(CommentService.class.getName());

    public void publishComment(Comment comment) {
        logger.info("Publishing comment: " + comment.getText());
    }

    @ToLog
    public void deleteComment(Comment comment) {
        logger.info("Deleting comment: " + comment.getText());
    }
}
```

Output:
```
Sep 09, 2026 9:29:26 PM services.CommentService publishComment
INFO: Publishing comment: I bought a Parrot
Sep 09, 2026 9:29:26 PM proxy.LoggingAspect log
INFO: Method will execute
Sep 09, 2026 9:29:26 PM services.CommentService deleteComment
INFO: Deleting comment: I bought a Parrot
Sep 09, 2026 9:29:26 PM proxy.LoggingAspect log
INFO: Method executed
```