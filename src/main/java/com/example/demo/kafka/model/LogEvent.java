package com.example.demo.kafka.model;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogEvent {

    private String traceId;
    private String className;
    private String methodName;
    private String httpMethod;
    private String requestUri;
    private String requestParam;
    private String responseBody;
    private Long executionTime;
    private String errorMessage;
    private String clientIp;
    private String userId;
}
