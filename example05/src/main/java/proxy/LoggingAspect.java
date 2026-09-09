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
