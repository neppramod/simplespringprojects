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
