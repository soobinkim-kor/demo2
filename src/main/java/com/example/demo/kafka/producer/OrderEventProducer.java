package com.example.demo.kafka.producer;

import com.example.demo.kafka.core.producer.AbstractKafkaProducer;
import com.example.demo.kafka.model.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderEventProducer extends AbstractKafkaProducer<OrderCreatedEvent> {

    private static final String TOPIC = "order.created";

    public OrderEventProducer(@Qualifier("orderKafkaTemplate") KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate) {
        super(kafkaTemplate);
    }

    public void sendOrderCreated(OrderCreatedEvent event) {
        send(TOPIC, String.valueOf(event.getOrderNo()), event);
    }

    @Override
    protected void handleSendSuccess(String topic, OrderCreatedEvent message, SendResult<String, OrderCreatedEvent> result) {
        log.info("OrderCreatedEvent sent: orderNo={}", message.getOrderNo());
    }

    @Override
    protected void handleSendFailure(String topic, OrderCreatedEvent message, Throwable ex) {
        log.error("Failed to send OrderCreatedEvent: orderNo={}", message.getOrderNo(), ex);
    }
}
