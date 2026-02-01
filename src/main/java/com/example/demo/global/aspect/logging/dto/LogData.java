package com.example.demo.global.aspect.logging.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogData {

    /** ===== Trace ===== */
    private String traceId;

    /** ===== Type ===== */
    private String logType;   // INFO, ERROR, ACCESS, SLOW

    /** ===== Method Info ===== */
    private String className;
    private String methodName;

    /** ===== HTTP ===== */
    private String httpMethod;
    private String requestUri;

    /** ===== User ===== */
    private String userId;
    private String clientIp;

    /** ===== Performance ===== */
    private Long executionTime;

    /** ===== Payload ===== */
    private String requestParam;
    private String responseBody;

    /** ===== Error ===== */
    private String errorMessage;
    private String stackTrace;
}
