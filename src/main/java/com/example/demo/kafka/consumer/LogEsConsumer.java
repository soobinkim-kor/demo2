package com.example.demo.kafka.consumer;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.example.demo.global.aspect.logging.event.LogEvent;
import com.example.demo.kafka.core.consumer.AbstractKafkaConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class LogEsConsumer extends AbstractKafkaConsumer<LogEvent> {

    private static final String INDEX_PREFIX = "app-log-";

    private final ElasticsearchClient elasticsearchClient;

    @KafkaListener(
            topics = "app-log-topic",
            groupId = "log-es-consumer",
            containerFactory = "logKafkaListenerContainerFactory"  // 이거 추가!
    )
    public void listen(ConsumerRecord<String, LogEvent> record, Acknowledgment ack) {
        processMessage(record, ack);
    }

    @Override
    public void consume(LogEvent message) {
        try {

            String indexName =
                    INDEX_PREFIX + LocalDate.now();

            IndexRequest<LogEvent> request =
                    IndexRequest.of(i -> i
                            .index(indexName)
                            .document(message)
                    );

            IndexResponse response = elasticsearchClient.index(request);

        } catch (Exception e) {
            log.error("Elasticsearch 저장 실패", e);
        }
    }

    @Override
    protected void beforeConsume(LogEvent message) {
        // 로그 이벤트 검증
    }

    @Override
    public void handleFailure(LogEvent message, Exception ex) {
        super.handleFailure(message, ex);
        // Elasticsearch 저장 실패 시 재시도 또는 DLQ로 전송
        sendToDeadLetterQueue(message, ex);
    }

    @Override
    protected boolean shouldAcknowledgeOnFailure() {
        // DLQ로 보냈으니 커밋
        return true;
    }

    private void sendToDeadLetterQueue(LogEvent message, Exception ex) {
        // DLQ 전송 로직
        log.error("Sending to DLQ: {}", message);
    }
}