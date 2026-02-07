package com.example.demo.kafka.core;

import org.apache.kafka.clients.producer.Producer;

public interface ProducerFactory<K, V> {
    Producer<K, V> createProducer();
}

