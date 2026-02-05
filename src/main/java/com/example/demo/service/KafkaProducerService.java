package com.example.demo.service;

import com.example.demo.global.aspect.logging.event.LogEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, LogEvent> kafkaTemplate;

    public void sendMessage(LogEvent message) {
        kafkaTemplate.send("log-event", message);
    }
}
