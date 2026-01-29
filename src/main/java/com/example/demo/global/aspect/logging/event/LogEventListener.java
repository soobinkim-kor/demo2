package com.example.demo.global.aspect.logging.event;

import com.example.demo.service.LoggingService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class LogEventListener {

    private final LoggingService loggingService;

    public LogEventListener(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @Async("loggingExecutor")
    @EventListener
    public void handle(LogEvent event) {
        loggingService.save(event.getLogData());
    }
}
