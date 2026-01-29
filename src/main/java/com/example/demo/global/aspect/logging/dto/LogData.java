package com.example.demo.global.aspect.logging.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@AllArgsConstructor
public class LogData {

    private String method;
    private String args;
    private boolean success;
    private String errorMessage;
    private long duration;
}
