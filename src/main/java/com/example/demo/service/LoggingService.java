package com.example.demo.service;

import com.example.demo.global.aspect.logging.dto.LogData;
import com.example.demo.global.aspect.logging.event.LogEvent;
import com.example.demo.global.aspect.logging.repository.LoggingEventRepository;
import com.example.demo.global.aspect.logging.entity.LoggingEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoggingService {

    private final LoggingEventRepository loggingRepository;

    public void save(LogEvent logEvent) {

        LogData logData = logEvent.getLogData();

        LoggingEvent entity = LoggingEvent.builder()
                .traceId(logData.getTraceId())
                .className(logData.getClassName())
                .logType(logData.getLogType())
                .methodName(logData.getMethodName())
                .createdAt(logData.getCreatedAt())
                .executionTime(logData.getExecutionTime())
                .errorMessage(logData.getErrorMessage())
                .build();

        loggingRepository.save(entity);
    }
}
