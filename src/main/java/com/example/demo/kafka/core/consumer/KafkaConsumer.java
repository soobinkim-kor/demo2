package com.example.demo.kafka.core.consumer;

public interface KafkaConsumer<T> {
    /**
     * 메시지 처리
     */
    void consume(T message);

    /**
     * 메시지 처리 (with metadata)
     */
    void consume(T message, String key, int partition, long offset);

    /**
     * 처리 실패 시 호출
     */
    void handleFailure(T message, Exception ex);
}
