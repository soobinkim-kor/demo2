package com.example.demo.service;

import com.example.demo.kafka.producer.LogEsProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LogEsProducerTest {

    @Mock
    KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    LogEsProducer logProducer;

    @Test
    void sendTest(){

        logProducer.send("hello");

        verify(kafkaTemplate).send("log-topic", "hello");
    }
}

