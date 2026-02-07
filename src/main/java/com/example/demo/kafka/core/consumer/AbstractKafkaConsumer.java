package com.example.demo.kafka.core.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.Acknowledgment;

@Slf4j
public abstract class AbstractKafkaConsumer<T> implements KafkaConsumer<T> {

    /**
     * 메시지 처리 템플릿 메서드
     */
    protected void processMessage(ConsumerRecord<String, T> record, Acknowledgment ack) {
        try {
            log.debug("Consuming message from topic: {}, partition: {}, offset: {}",
                    record.topic(), record.partition(), record.offset());

            // 전처리 훅
            beforeConsume(record.value());

            // 실제 처리
            consume(record.value(), record.key(), record.partition(), record.offset());

            // 후처리 훅
            afterConsume(record.value());

            // 수동 커밋 (AckMode.MANUAL일 경우)
            if (ack != null) {
                ack.acknowledge();
            }

        } catch (Exception ex) {
            log.error("Failed to consume message from topic: {}, offset: {}",
                    record.topic(), record.offset(), ex);
            handleFailure(record.value(), ex);

            // 실패 시 커밋 전략에 따라 처리
            if (shouldAcknowledgeOnFailure()) {
                if (ack != null) {
                    ack.acknowledge();
                }
            }
        }
    }

    @Override
    public void consume(T message, String key, int partition, long offset) {
        // 기본 구현: 간단한 consume 호출
        consume(message);
    }

    /**
     * 실제 비즈니스 로직 (하위 클래스에서 구현)
     */
    @Override
    public abstract void consume(T message);

    /**
     * 실패 처리 (하위 클래스에서 오버라이드 가능)
     */
    @Override
    public void handleFailure(T message, Exception ex) {
        // 기본: 로깅만
        log.error("Message processing failed: {}", message, ex);
    }

    // 훅 메서드들 (필요시 오버라이드)

    protected void beforeConsume(T message) {
        // 전처리 로직 (validation 등)
    }

    protected void afterConsume(T message) {
        // 후처리 로직 (메트릭 수집 등)
    }

    protected boolean shouldAcknowledgeOnFailure() {
        // 실패 시에도 커밋할지 여부 (DLQ 사용 시 true)
        return false;
    }
}