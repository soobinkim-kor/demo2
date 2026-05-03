package com.example.demo.kafka.producer;

import com.example.demo.kafka.core.producer.AbstractKafkaProducer;
import com.example.demo.kafka.model.InventoryRestoreEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InventoryRestoreProducer extends AbstractKafkaProducer<InventoryRestoreEvent> {

    private static final String TOPIC = "inventory.restore";

    public InventoryRestoreProducer(@Qualifier("inventoryRestoreKafkaTemplate") KafkaTemplate<String, InventoryRestoreEvent> kafkaTemplate) {
        super(kafkaTemplate);
    }

    public void send(InventoryRestoreEvent event) {
        send(TOPIC, String.valueOf(event.getOrderNo()), event);
    }

    @Override
    protected void handleSendSuccess(String topic, InventoryRestoreEvent message, SendResult<String, InventoryRestoreEvent> result) {
        log.info("inventory.restore sent: orderNo={}", message.getOrderNo());
    }

    @Override
    protected void handleSendFailure(String topic, InventoryRestoreEvent message, Throwable ex) {
        log.error("Failed to send inventory.restore: orderNo={}", message.getOrderNo(), ex);
    }
}
