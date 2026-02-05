package com.example.demo.kafka.consumer;

import com.example.demo.global.aspect.logging.entity.LoggingEvent;
import com.example.demo.global.aspect.logging.event.LogEvent;
import com.example.demo.global.aspect.logging.mapper.LogMapper;
import com.example.demo.service.LoggingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LogEventConsumer {

    private final LoggingService loggingService;

    @KafkaListener(
            topics = "log-event",
            groupId = "log-group"
    )
    public void consume(LogEvent event) {
        loggingService.save(event);
    }
}
