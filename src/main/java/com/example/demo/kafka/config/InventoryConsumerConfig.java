package com.example.demo.kafka.config;

import com.example.demo.kafka.core.config.AbstractKafkaConsumerConfig;
import com.example.demo.kafka.model.InventoryFailedEvent;
import com.example.demo.kafka.model.InventoryReservedEvent;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

import java.util.HashMap;

@Configuration
public class InventoryConsumerConfig {

    @Configuration
    static class ReservedConfig extends AbstractKafkaConsumerConfig<InventoryReservedEvent> {
        ReservedConfig(KafkaProperties kafkaProperties) { super(kafkaProperties); }

        @Bean(name = "inventoryReservedConsumerFactory")
        public ConsumerFactory<String, InventoryReservedEvent> inventoryReservedConsumerFactory() {
            return createConsumerFactory(InventoryReservedEvent.class, new HashMap<>());
        }

        @Bean(name = "inventoryReservedListenerFactory")
        public ConcurrentKafkaListenerContainerFactory<String, InventoryReservedEvent> inventoryReservedListenerFactory() {
            return createListenerContainerFactory(inventoryReservedConsumerFactory());
        }
    }

    @Configuration
    static class FailedConfig extends AbstractKafkaConsumerConfig<InventoryFailedEvent> {
        FailedConfig(KafkaProperties kafkaProperties) { super(kafkaProperties); }

        @Bean(name = "inventoryFailedConsumerFactory")
        public ConsumerFactory<String, InventoryFailedEvent> inventoryFailedConsumerFactory() {
            return createConsumerFactory(InventoryFailedEvent.class, new HashMap<>());
        }

        @Bean(name = "inventoryFailedListenerFactory")
        public ConcurrentKafkaListenerContainerFactory<String, InventoryFailedEvent> inventoryFailedListenerFactory() {
            return createListenerContainerFactory(inventoryFailedConsumerFactory());
        }
    }
}
