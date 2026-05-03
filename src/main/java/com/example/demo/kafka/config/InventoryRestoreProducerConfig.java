package com.example.demo.kafka.config;

import com.example.demo.kafka.core.config.AbstractKafkaProducerConfig;
import com.example.demo.kafka.model.InventoryRestoreEvent;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;

@Configuration
public class InventoryRestoreProducerConfig extends AbstractKafkaProducerConfig<InventoryRestoreEvent> {

    public InventoryRestoreProducerConfig(KafkaProperties kafkaProperties) {
        super(kafkaProperties);
    }

    @Bean(name = "inventoryRestoreProducerFactory")
    public ProducerFactory<String, InventoryRestoreEvent> inventoryRestoreProducerFactory() {
        return createProducerFactory(new HashMap<>());
    }

    @Bean(name = "inventoryRestoreKafkaTemplate")
    public KafkaTemplate<String, InventoryRestoreEvent> inventoryRestoreKafkaTemplate() {
        return createKafkaTemplate(inventoryRestoreProducerFactory());
    }
}
