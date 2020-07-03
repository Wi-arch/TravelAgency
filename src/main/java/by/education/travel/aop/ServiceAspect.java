package by.education.travel.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Arrays.asList;

@Aspect
@Component
@Slf4j
public class ServiceAspect {

    @Pointcut("execution(* by.education.travel.service.*.*(..))")
    public void doSomething() {
    }

    @Before(value = "doSomething()", argNames = "joinPoint")
    public void beforeCallLogger(JoinPoint joinPoint) {
        List<Object> arguments = asList(joinPoint.getArgs());
        if (arguments.isEmpty()) {
            log.info("Start method: {} --- With no arguments", joinPoint.getSignature().toShortString());
        } else {
            log.info("Start method: {} --- With arguments: {}", joinPoint.getSignature().toShortString(), arguments);
        }
    }

    @AfterReturning(value = "doSomething()", returning = "result")
    public void afterCallLogger(JoinPoint joinPoint, Object result) {
        log.info("End method: {} --- With return value: {}", joinPoint.getSignature().toShortString(), result);
    }
}
