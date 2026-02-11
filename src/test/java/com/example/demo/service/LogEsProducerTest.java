package com.example.demo.service;

import com.example.demo.global.aspect.logging.dto.LogData;
import com.example.demo.global.aspect.logging.event.LogEvent;
import com.example.demo.kafka.producer.LogEsProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LogEsProducerTest {

    @Mock
    KafkaTemplate<String, LogEvent> kafkaTemplate;

    @InjectMocks
    LogEsProducer logProducer;

    @Test
    void sendTest(){
        LogData logData = new LogData();

        LogEvent logEvent = LogEvent.builder().logData(logData).build();
        logProducer.send("log-topic",logEvent);

        verify(kafkaTemplate)
                .send(eq("app-log-topic"), any(LogEvent.class));
    }
}

