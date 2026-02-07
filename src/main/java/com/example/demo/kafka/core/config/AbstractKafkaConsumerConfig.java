package com.example.demo.kafka.core.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public abstract class AbstractKafkaConsumerConfig<T> {

    protected final KafkaProperties kafkaProperties;

    protected ConsumerFactory<String, T> createConsumerFactory(Class<T> targetType) {
        return createConsumerFactory(targetType, new HashMap<>());
    }

    protected ConsumerFactory<String, T> createConsumerFactory(
            Class<T> targetType,
            Map<String, Object> additionalProps) {

        Map<String, Object> props = new HashMap<>();

        // ========== Consumer 설정 (Producer 아님!) ==========
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                kafkaProperties.getBootstrapServers());

        // Key Deserializer
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        // Value Deserializer - ErrorHandlingDeserializer로 래핑
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, targetType);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);

        // Consumer 동작 설정
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false); // 수동 커밋
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 10);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 30000);
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 3000);

        // 추가 설정 병합
        props.putAll(additionalProps);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    protected ConcurrentKafkaListenerContainerFactory<String, T> createListenerContainerFactory(
            ConsumerFactory<String, T> consumerFactory) {

        ConcurrentKafkaListenerContainerFactory<String, T> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);

        // MANUAL AckMode 설정
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);

        // 동시성 설정
        factory.setConcurrency(getConcurrency());

        // 배치 리스너 여부
        factory.setBatchListener(false);

        // 에러 핸들러 설정
        factory.setCommonErrorHandler(createErrorHandler());

        return factory;
    }

    protected DefaultErrorHandler createErrorHandler() {
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(
                (record, exception) -> {
                    handleRecovery(record, exception);
                },
                new FixedBackOff(2000L, 3L)
        );

        errorHandler.addNotRetryableExceptions(
                IllegalArgumentException.class,
                NullPointerException.class
        );

        return errorHandler;
    }

    protected void handleRecovery(org.apache.kafka.clients.consumer.ConsumerRecord<?, ?> record, Exception exception) {
        System.err.println("All retries exhausted for record: " + record + ", exception: " + exception.getMessage());
    }

    protected int getConcurrency() {
        return 3;
    }
}