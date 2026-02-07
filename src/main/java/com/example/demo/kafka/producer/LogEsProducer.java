package com.example.demo.kafka.producer;

import com.example.demo.global.aspect.logging.event.LogEvent;
import com.example.demo.kafka.model.LogMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogEsProducer {
    private static final String TOPIC = "app-log-topic";

    private final KafkaTemplate<String, LogMessage> kafkaTemplate;

    public void send(LogMessage message) {
        kafkaTemplate.send(TOPIC, message);
    }
}
