package com.example.demo.global.aspect;

import com.example.demo.global.aspect.logging.dto.LogData;
import com.example.demo.global.aspect.logging.event.LogEvent;
import com.example.demo.global.aspect.logging.factory.LogFactory;

import com.example.demo.service.KafkaProducerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
public class RequestLoggingAspect {

    private final ApplicationEventPublisher publisher;
    private final LogFactory logFactory;
    private final KafkaProducerService kafkaProducerService;

    @Around("execution(* com.example.demo..controller..*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();

        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                        .getRequest();

        try {
            Object result = joinPoint.proceed();

            LogData data =
                    logFactory.createAccessLog(joinPoint, request, start);

            kafkaProducerService.sendMessage(new LogEvent(data));

            return result;

        } catch (Exception e) {

            LogData data =
                    logFactory.createErrorLog(joinPoint, request, start, e);

            publisher.publishEvent(new LogEvent(data));
            throw e;
        }
    }
}
