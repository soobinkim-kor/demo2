package com.example.demo.kafka.producer;

import com.example.demo.global.aspect.logging.event.LogEvent;
import com.example.demo.kafka.core.producer.AbstractKafkaProducer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
public class LogEventProducer extends AbstractKafkaProducer<LogEvent> {
    private static final String TOPIC = "log-event";

    public void send(LogEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }

    public LogEventProducer(@Qualifier("logMessageKafkaTemplate") KafkaTemplate<String, LogEvent> kafkaTemplate){
        super(kafkaTemplate);
    }

    @Override
    protected void handleSendSuccess(String topic, LogEvent message, SendResult<String, LogEvent> result) {

    }

    @Override
    protected void handleSendFailure(String topic, LogEvent message, Throwable ex) {

    }
}
