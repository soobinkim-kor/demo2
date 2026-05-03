package com.example.demo.kafka.consumer;

import com.example.demo.kafka.model.InventoryFailedEvent;
import com.example.demo.kafka.model.InventoryReservedEvent;
import com.example.demo.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InventoryResultConsumer {

    private final OrderService orderService;

    public InventoryResultConsumer(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(
            topics = "inventory.reserved",
            groupId = "order-inventory-reserved-consumer",
            containerFactory = "inventoryReservedListenerFactory"
    )
    public void listenReserved(ConsumerRecord<String, InventoryReservedEvent> record, Acknowledgment ack) {
        try {
            log.info("inventory.reserved received: orderNo={}", record.value().getOrderNo());
            orderService.handleInventoryReserved(record.value());
            ack.acknowledge();
        } catch (Exception e) {
            log.error("Failed to handle inventory.reserved: orderNo={}", record.value().getOrderNo(), e);
        }
    }

    @KafkaListener(
            topics = "inventory.failed",
            groupId = "order-inventory-failed-consumer",
            containerFactory = "inventoryFailedListenerFactory"
    )
    public void listenFailed(ConsumerRecord<String, InventoryFailedEvent> record, Acknowledgment ack) {
        try {
            log.info("inventory.failed received: orderNo={}, reason={}", record.value().getOrderNo(), record.value().getReason());
            orderService.handleInventoryFailed(record.value());
            ack.acknowledge();
        } catch (Exception e) {
            log.error("Failed to handle inventory.failed: orderNo={}", record.value().getOrderNo(), e);
        }
    }
}
