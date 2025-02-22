package com.example.t1tasktracker.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    @Around("target(com.example.t1tasktracker.repository.TaskRepository)")
    public Object taskRepositoryBenchmark(ProceedingJoinPoint joinPoint) {
        log.debug("Measuring {} method..", joinPoint.getSignature().getName());
        long startTime = System.currentTimeMillis();

        Object proceededResult;
        try {
            proceededResult = joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(String.format("Error when executing the method %s with arguments %s",
                    joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs())), e);
        }

        long endTime = System.currentTimeMillis();
        log.debug("Method {} executed in {} ms", joinPoint.getSignature().getName(), endTime - startTime);

        return proceededResult;
    }

    @Before("@annotation(com.example.t1tasktracker.aspect.annotation.LogBefore)")
    public void logBefore(JoinPoint joinPoint) {
        log.debug("Executing method {} with arguments {} of {}", joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()), joinPoint.getTarget().getClass());
    }

    @AfterThrowing(value = "@annotation(com.example.t1tasktracker.aspect.annotation.LogException)", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
        log.warn("Exception {} while executing method {}", ex.getMessage(), joinPoint.getSignature().getName());
    }

    @AfterReturning(value = "@annotation(com.example.t1tasktracker.aspect.annotation.LogReturning)", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        log.info("Method {} executed successfully. Result: {}", joinPoint.getSignature().getName(), result);
    }

}
