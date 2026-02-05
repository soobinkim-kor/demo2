package com.example.demo.global.aspect.logging.service;

import com.example.demo.global.aspect.logging.dto.LogData;
import com.example.demo.global.aspect.logging.entity.LoggingEvent;
import com.example.demo.global.aspect.logging.mapper.LogMapper;
import com.example.demo.global.aspect.logging.repository.LoggingEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AspectLoggingService {

    private final LoggingEventRepository repository;

    @Transactional
    public void save(LogData logData) {

        try {
            LoggingEvent entity = LogMapper.toEntity(logData);
            repository.save(entity);
        } catch (Exception e) {
            // 로깅 실패해도 비즈니스 로직 영향 X
            System.err.println("[LOGGING-FAIL] " + e.getMessage());
        }
    }
}
