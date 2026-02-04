package com.example.demo.kafka.consumer;

import com.example.demo.kafka.model.LogEvent;
import com.example.demo.service.LoggingService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
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
