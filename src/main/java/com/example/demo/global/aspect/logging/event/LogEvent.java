package com.example.demo.global.aspect.logging.event;

import com.example.demo.global.aspect.logging.dto.LogData;
import lombok.Getter;

@Getter
public class LogEvent {

    private final LogData logData;

    public LogEvent(LogData logData) {
        this.logData = logData;
    }
}
