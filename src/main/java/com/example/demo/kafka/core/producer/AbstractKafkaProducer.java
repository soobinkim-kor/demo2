package com.example.demo.kafka.core.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractKafkaProducer<T> implements KafkaProducer<T> {

    protected final KafkaTemplate<String, T> kafkaTemplate;

    @Override
    public void send(String topic, T message) {
        kafkaTemplate.send(topic, message)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to send message to topic: {}", topic, ex);
                        handleSendFailure(topic, message, ex);
                    } else {
                        log.debug("Message sent successfully to topic: {}", topic);
                        handleSendSuccess(topic, message, result);
                    }
                });
    }

    @Override
    public CompletableFuture<SendResult<String, T>> sendAsync(String topic, T message) {
        return kafkaTemplate.send(topic, message);
    }

    @Override
    public void send(String topic, String key, T message) {
        kafkaTemplate.send(topic, key, message)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to send message with key {} to topic: {}", key, topic, ex);
                        handleSendFailure(topic, message, ex);
                    }
                });
    }

    protected abstract void handleSendSuccess(String topic, T message, SendResult<String, T> result);

    protected abstract void handleSendFailure(String topic, T message, Throwable ex);
}