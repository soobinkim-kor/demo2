package com.example.demo.kafka.config;

import com.example.demo.global.aspect.logging.event.LogEvent;
import com.example.demo.kafka.core.config.AbstractKafkaConsumerConfig;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class EsConsumerConfig extends AbstractKafkaConsumerConfig<LogEvent> {

    public EsConsumerConfig(KafkaProperties kafkaProperties) {
        super(kafkaProperties);
    }

    @Bean(name = "logConsumerFactory")
    public ConsumerFactory<String, LogEvent> logConsumerFactory() {
        Map<String, Object> additionalProps =  new HashMap<>();

        return createConsumerFactory(LogEvent.class, additionalProps);
    }

    @Bean(name = "logKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, LogEvent> logKafkaListenerContainerFactory() {
        return createListenerContainerFactory(logConsumerFactory());
    }
}