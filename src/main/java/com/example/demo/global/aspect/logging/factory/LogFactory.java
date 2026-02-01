package com.example.demo.global.aspect.logging.factory;

import com.example.demo.global.aspect.logging.dto.LogData;
import com.example.demo.global.aspect.logging.type.LogType;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;

@Component
public class LogFactory {

    public LogData createAccessLog(
            ProceedingJoinPoint joinPoint,
            HttpServletRequest request,
            long startTime
    ) {
        return base(joinPoint, request, startTime)
                .logType(LogType.ACCESS.name())
                .build();
    }

    public LogData createErrorLog(
            ProceedingJoinPoint joinPoint,
            HttpServletRequest request,
            long startTime,
            Exception e
    ) {
        return base(joinPoint, request, startTime)
                .logType(LogType.ERROR.name())
                .errorMessage(e.getMessage())
                .stackTrace(Arrays.toString(e.getStackTrace()))
                .build();
    }

    private LogData.LogDataBuilder base(
            ProceedingJoinPoint joinPoint,
            HttpServletRequest request,
            long startTime
    ) {
        return LogData.builder()
                .traceId(UUID.randomUUID().toString())
                .className(joinPoint.getSignature().getDeclaringTypeName())
                .methodName(joinPoint.getSignature().getName())
                .httpMethod(request.getMethod())
                .requestUri(request.getRequestURI())
                .clientIp(request.getRemoteAddr())
                .executionTime(System.currentTimeMillis() - startTime)
                .requestParam(Arrays.toString(joinPoint.getArgs()));
    }
}
