package com.example.demo.kafka.config;

import com.example.demo.global.aspect.logging.event.LogEvent;
import com.example.demo.kafka.core.config.AbstractKafkaProducerConfig;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class EsProducerConfig extends AbstractKafkaProducerConfig<LogEvent> {
    public EsProducerConfig(KafkaProperties kafkaProperties) {
        super(kafkaProperties);
    }

    @Bean(name  = "esLogProducerFactory")
    public ProducerFactory<String, LogEvent> logProducerFactory() {
        Map<String, Object> addProp = new HashMap<>();

        return createProducerFactory(addProp);
    }


    @Bean(name = "esLogKafkaTemplate")
    public KafkaTemplate<String, LogEvent> logKafkaTemplate() {
        return createKafkaTemplate(logProducerFactory());
    }
}
