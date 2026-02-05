package com.example.demo.global.aspect.logging.listener;

import com.example.demo.global.aspect.logging.event.LogEvent;
import com.example.demo.global.aspect.logging.service.AspectLoggingService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogEventListener {

    private final AspectLoggingService aspectLoggingService;

    @Async("loggingExecutor")
    @EventListener
    public void handle(LogEvent event) {
        aspectLoggingService.save(event.getLogData());
    }
}
