package com.example.demo.kafka.producer;

import com.example.demo.global.aspect.logging.event.LogEvent;
import com.example.demo.kafka.core.producer.AbstractKafkaProducer;
import com.example.demo.kafka.model.LogMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
public class LogEsProducer extends AbstractKafkaProducer<LogEvent> {
    private static final String TOPIC = "app-log-topic";

    public LogEsProducer(@Qualifier("esLogKafkaTemplate")KafkaTemplate<String, LogEvent> kafkaTemplate){
        super(kafkaTemplate);
    }

    public void send(LogEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }

    @Override
    protected void handleSendSuccess(String topic, LogEvent message, SendResult<String, LogEvent> result) {

    }

    @Override
    protected void handleSendFailure(String topic, LogEvent message, Throwable ex) {

    }
}
