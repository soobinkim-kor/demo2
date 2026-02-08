package com.example.demo.global.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

    @Value("${spring.elasticsearch.uris}")
    private String uri;

    @Bean
    public ElasticsearchClient elasticsearchClient() {
        // RestClient 생성
        RestClient restClient = RestClient.builder(
                HttpHost.create(uri)
        ).build();

        // ObjectMapper에 JavaTimeModule 등록
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // JacksonJsonpMapper에 커스텀 ObjectMapper 전달
        JacksonJsonpMapper jsonpMapper = new JacksonJsonpMapper(objectMapper);

        // Transport 생성
        ElasticsearchTransport transport = new RestClientTransport(
                restClient,
                jsonpMapper
        );

        // ElasticsearchClient 생성
        return new ElasticsearchClient(transport);
    }
}