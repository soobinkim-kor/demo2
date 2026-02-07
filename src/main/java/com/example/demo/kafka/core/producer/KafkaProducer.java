package com.example.demo.kafka.core.producer;

import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

public interface KafkaProducer<T> {

    void send(String topic, T message);

    void send(String topic, String key, T message);

    CompletableFuture<SendResult<String, T>> sendAsync(String topic, T message);
}
