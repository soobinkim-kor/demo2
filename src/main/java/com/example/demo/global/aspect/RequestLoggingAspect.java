package com.example.demo.global.aspect;

import com.example.demo.global.aspect.logging.dto.LogData;
import com.example.demo.global.aspect.logging.event.LogEvent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Aspect
@Component
public class RequestLoggingAspect {

    private final ApplicationEventPublisher publisher;

    public RequestLoggingAspect(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Around("execution(* com.example.demo..controller..*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();

            LogData data = new LogData(
                    joinPoint.getSignature().toShortString(),
                    Arrays.toString(joinPoint.getArgs()),
                    true,
                    null,
                    System.currentTimeMillis() - start
            );

            publisher.publishEvent(new LogEvent(data));

            return result;

        } catch (Exception e) {

            LogData data = new LogData(
                    joinPoint.getSignature().toShortString(),
                    Arrays.toString(joinPoint.getArgs()),
                    false,
                    e.getMessage(),
                    System.currentTimeMillis() - start
            );

            publisher.publishEvent(new LogEvent(data));

            throw e;
        }
    }
}
