package com.example.demo.global.aspect.logging.repository;

import com.example.demo.global.aspect.logging.entity.LoggingEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoggingEventRepository extends JpaRepository<LoggingEvent, Long> {
}
