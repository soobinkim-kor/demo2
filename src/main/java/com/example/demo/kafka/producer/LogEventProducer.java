package com.example.demo.kafka.producer;

import com.example.demo.global.aspect.logging.event.LogEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogEventProducer {

    private static final String TOPIC = "log-event";

    private final KafkaTemplate<String, LogEvent> kafkaTemplate;

    public void send(LogEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }
}
