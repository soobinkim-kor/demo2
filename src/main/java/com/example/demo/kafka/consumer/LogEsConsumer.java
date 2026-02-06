package com.example.demo.kafka.consumer;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;

import com.example.demo.kafka.model.LogMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class LogEsConsumer {

    private final ElasticsearchClient elasticsearchClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String INDEX_PREFIX = "app-log-";

    @KafkaListener(
            topics = "app-log-topic",
            groupId = "log-es-consumer"
    )
    public void consume(String message) {

        try {
            LogMessage logMessage =
                    objectMapper.readValue(message, LogMessage.class);

            String indexName =
                    INDEX_PREFIX + LocalDate.now();

            IndexRequest<LogMessage> request =
                    IndexRequest.of(i -> i
                            .index(indexName)
                            .document(logMessage)
                    );

            elasticsearchClient.index(request);

        } catch (Exception e) {
            log.error("Elasticsearch 저장 실패", e);
        }
    }
}
