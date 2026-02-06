package com.example.demo.kafka.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogMessage {

    private String traceId;
    private String serviceName;

    private String className;
    private String methodName;

    private String httpMethod;
    private String requestUri;

    private String requestParam;
    private String responseBody;

    private String errorMessage;
    private Long executionTime;

    private String userId;
    private LocalDateTime createdAt;
}
