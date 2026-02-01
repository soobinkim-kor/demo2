package com.example.demo.global.aspect.logging.mapper;

import com.example.demo.global.aspect.logging.dto.LogData;
import com.example.demo.global.aspect.logging.entity.LoggingEvent;


import java.time.LocalDateTime;

public class LogMapper {

    public static LoggingEvent toEntity(LogData data) {
        return LoggingEvent.builder()
                .traceId(data.getTraceId())
                .logType(data.getLogType())
                .className(data.getClassName())
                .methodName(data.getMethodName())
                .httpMethod(data.getHttpMethod())
                .requestUri(data.getRequestUri())
                .userId(data.getUserId())
                .clientIp(data.getClientIp())
                .executionTime(data.getExecutionTime())
//                .requestParam(data.getRequestParam())
                .responseBody(data.getResponseBody())
                .errorMessage(data.getErrorMessage())
                .stackTrace(data.getStackTrace())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
