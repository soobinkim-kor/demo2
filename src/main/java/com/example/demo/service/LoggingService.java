package com.example.demo.service;

import com.example.demo.kafka.model.LogEvent;
import com.example.demo.global.aspect.logging.repository.LoggingEventRepository;
import com.example.demo.global.aspect.logging.entity.LoggingEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoggingService {

    private final LoggingEventRepository loggingRepository;

    public void save(LogEvent event) {

        LoggingEvent entity = LoggingEvent.builder()
                .traceId(event.getTraceId())
                .className(event.getClassName())
                .methodName(event.getMethodName())
                .executionTime(event.getExecutionTime())
                .errorMessage(event.getErrorMessage())
                .build();

        loggingRepository.save(entity);
    }
}
