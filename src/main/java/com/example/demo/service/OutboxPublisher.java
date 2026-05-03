package com.example.demo.service;

import com.example.demo.entity.OutboxEntity;
import com.example.demo.entity.OutboxStatus;
import com.example.demo.kafka.model.InventoryRestoreEvent;
import com.example.demo.kafka.model.OrderCreatedEvent;
import com.example.demo.kafka.producer.InventoryRestoreProducer;
import com.example.demo.kafka.producer.OrderEventProducer;
import com.example.demo.repository.order.OutboxRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxPublisher {

    private final OutboxRepository outboxRepository;
    private final OrderEventProducer orderEventProducer;
    private final InventoryRestoreProducer inventoryRestoreProducer;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void publish() {
        List<OutboxEntity> pending = outboxRepository.findByStatus(OutboxStatus.PENDING);
        if (pending.isEmpty()) return;

        log.debug("Outbox: {} pending events found", pending.size());

        for (OutboxEntity event : pending) {
            try {
                switch (event.getEventType()) {
                    case ORDER_CREATED -> {
                        OrderCreatedEvent e = objectMapper.readValue(event.getPayload(), OrderCreatedEvent.class);
                        orderEventProducer.sendOrderCreated(e);
                    }
                    case INVENTORY_RESTORE -> {
                        InventoryRestoreEvent e = objectMapper.readValue(event.getPayload(), InventoryRestoreEvent.class);
                        inventoryRestoreProducer.send(e);
                    }
                }
                event.markPublished();
                log.info("Outbox event published: type={}, aggregateId={}", event.getEventType(), event.getAggregateId());
            } catch (Exception e) {
                event.markFailed();
                log.error("Outbox event failed: type={}, aggregateId={}", event.getEventType(), event.getAggregateId(), e);
            }
            outboxRepository.save(event);
        }
    }
}
