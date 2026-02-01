package com.example.demo.global.aspect.logging.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "logging_event")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class LoggingEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    private String traceId;
    private String logType;

    private String className;
    private String methodName;

    private String httpMethod;
    private String requestUri;

    private String userId;
    private String clientIp;

    private Long executionTime;

    @Column(columnDefinition = "json")
    private String requestParam;

    @Column(columnDefinition = "json")
    private String responseBody;

    @Column(columnDefinition = "text")
    private String errorMessage;

    @Column(columnDefinition = "longtext")
    private String stackTrace;

    private LocalDateTime createdAt;
}
