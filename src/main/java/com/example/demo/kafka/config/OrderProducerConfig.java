package com.example.demo.kafka.config;

import com.example.demo.kafka.core.config.AbstractKafkaProducerConfig;
import com.example.demo.kafka.model.OrderCreatedEvent;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;

@Configuration
public class OrderProducerConfig extends AbstractKafkaProducerConfig<OrderCreatedEvent> {

    public OrderProducerConfig(KafkaProperties kafkaProperties) {
        super(kafkaProperties);
    }

    @Bean(name = "orderProducerFactory")
    public ProducerFactory<String, OrderCreatedEvent> orderProducerFactory() {
        return createProducerFactory(new HashMap<>());
    }

    @Bean(name = "orderKafkaTemplate")
    public KafkaTemplate<String, OrderCreatedEvent> orderKafkaTemplate() {
        return createKafkaTemplate(orderProducerFactory());
    }
}
