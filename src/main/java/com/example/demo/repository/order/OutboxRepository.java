package com.example.demo.repository.order;

import com.example.demo.entity.OutboxEntity;
import com.example.demo.entity.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboxRepository extends JpaRepository<OutboxEntity, Long> {
    List<OutboxEntity> findByStatus(OutboxStatus status);
}
