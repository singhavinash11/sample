package org.singhav.sample.aspect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    /**
     * Pointcut that matches all services and Web REST endpoints.
     */
    @Pointcut("within(@org.springframework.stereotype.Service *) || " +
            "within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut("execution(public * org.singhav.sample..*(..))")
    private void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Pointcut that matches method to be logged.
     */
    @Pointcut("@annotation(org.singhav.sample.aspect.LogExecutionTime)")
    private void logTimePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        var loggingDetails = getLoggingDetails(joinPoint);
        log.debug("=> {}.{}({}) using {}", loggingDetails.getClassName(), loggingDetails.getMethodName(), loggingDetails.getArgs(), Thread.currentThread());
        Object result = joinPoint.proceed();
        String resultString = result != null ? result.toString() : "Void response from method";
        log.debug("<= {}.{}({}) with result - {} using {}", loggingDetails.getClassName(), loggingDetails.getMethodName(), loggingDetails.getArgs(), resultString, Thread.currentThread());
        return result;
    }

    @Around("springBeanPointcut() && logTimePointcut()")
    public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        var loggingDetails = getLoggingDetails(joinPoint);
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - start;
        log.debug("<= Executed {}.{}({}) in {} ms using {}", loggingDetails.getClassName(), loggingDetails.getMethodName(), loggingDetails.getArgs(), duration, Thread.currentThread());
        return result;
    }

    private static LoggingDetails getLoggingDetails(ProceedingJoinPoint joinPoint) {
        var className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        var methodName = joinPoint.getSignature().getName();
        var args = Arrays.toString(joinPoint.getArgs());
        return new LoggingDetails(className, methodName, args);
    }

    @Getter
    @AllArgsConstructor
    private static class LoggingDetails {
        private String className;
        private String methodName;
        private String args;
    }
}
