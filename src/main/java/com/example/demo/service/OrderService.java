package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.global.error.BusinessException;
import com.example.demo.global.error.CommonErrorCode;
import com.example.demo.kafka.model.InventoryFailedEvent;
import com.example.demo.kafka.model.InventoryRestoreEvent;
import com.example.demo.kafka.model.InventoryReservedEvent;
import com.example.demo.kafka.model.OrderCreatedEvent;
import com.example.demo.repository.order.OrderRepository;
import com.example.demo.repository.order.OutboxRepository;
import com.example.demo.request.order.OrderCreateRequest;
import com.example.demo.response.order.OrderResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public OrderResponse createOrder(OrderCreateRequest request) {
        BigDecimal totalAmount = request.getItems().stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        OrderEntity order = OrderEntity.builder()
                .usrNo(request.getUsrNo())
                .orderStatus(OrderStatus.PENDING)
                .totalAmount(totalAmount)
                .build();

        List<OrderItemEntity> items = request.getItems().stream()
                .map(item -> OrderItemEntity.builder()
                        .order(order)
                        .productNo(item.getProductNo())
                        .productNm(item.getProductNm())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .build())
                .toList();

        order.getItems().addAll(items);
        OrderEntity saved = orderRepository.save(order);

        // Kafka 직접 발행 대신 Outbox에 저장 → DB 트랜잭션과 원자적으로 처리
        OrderCreatedEvent event = OrderCreatedEvent.builder()
                .orderNo(saved.getOrderNo())
                .usrNo(saved.getUsrNo())
                .totalAmount(saved.getTotalAmount())
                .items(request.getItems().stream()
                        .map(item -> OrderCreatedEvent.OrderItemPayload.builder()
                                .productNo(item.getProductNo())
                                .quantity(item.getQuantity())
                                .unitPrice(item.getUnitPrice())
                                .build())
                        .toList())
                .build();

        saveOutbox(OutboxEventType.ORDER_CREATED, String.valueOf(saved.getOrderNo()), event);
        log.info("Order created, outbox event saved: orderNo={}", saved.getOrderNo());

        return new OrderResponse(saved);
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrder(Long orderNo) {
        OrderEntity order = orderRepository.findById(orderNo)
                .orElseThrow(() -> new BusinessException(CommonErrorCode.RESOURCE_NOT_FOUND));
        return new OrderResponse(order);
    }

    @Transactional
    public void handleInventoryReserved(InventoryReservedEvent event) {
        OrderEntity order = orderRepository.findById(event.getOrderNo())
                .orElseThrow(() -> new BusinessException(CommonErrorCode.RESOURCE_NOT_FOUND));

        order.updateStatus(OrderStatus.RESERVED);

        try {
            processPayment(order);
            order.updateStatus(OrderStatus.CONFIRMED);
            log.info("Order confirmed: orderNo={}", order.getOrderNo());
        } catch (Exception e) {
            // 결제 실패 → 보상 트랜잭션: 재고 복구 이벤트를 Outbox에 저장
            log.warn("Payment failed, triggering compensation: orderNo={}", order.getOrderNo(), e);
            order.updateStatus(OrderStatus.PAYMENT_FAILED);

            InventoryRestoreEvent restoreEvent = InventoryRestoreEvent.builder()
                    .orderNo(order.getOrderNo())
                    .usrNo(order.getUsrNo())
                    .items(order.getItems().stream()
                            .map(item -> OrderCreatedEvent.OrderItemPayload.builder()
                                    .productNo(item.getProductNo())
                                    .quantity(item.getQuantity())
                                    .unitPrice(item.getUnitPrice())
                                    .build())
                            .toList())
                    .build();

            saveOutbox(OutboxEventType.INVENTORY_RESTORE, String.valueOf(order.getOrderNo()), restoreEvent);
        }

        orderRepository.save(order);
    }

    @Transactional
    public void handleInventoryFailed(InventoryFailedEvent event) {
        OrderEntity order = orderRepository.findById(event.getOrderNo())
                .orElseThrow(() -> new BusinessException(CommonErrorCode.RESOURCE_NOT_FOUND));

        order.updateStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        log.warn("Order cancelled - inventory unavailable: orderNo={}, reason={}", event.getOrderNo(), event.getReason());
    }

    private void processPayment(OrderEntity order) {
        // 실제 PG 연동 자리 - 실패 시 RuntimeException throw
        log.debug("Processing payment: orderNo={}, amount={}", order.getOrderNo(), order.getTotalAmount());
    }

    private void saveOutbox(OutboxEventType eventType, String aggregateId, Object payload) {
        try {
            String json = objectMapper.writeValueAsString(payload);
            OutboxEntity outbox = OutboxEntity.builder()
                    .aggregateId(aggregateId)
                    .eventType(eventType)
                    .payload(json)
                    .status(OutboxStatus.PENDING)
                    .build();
            outboxRepository.save(outbox);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Outbox 직렬화 실패: " + eventType, e);
        }
    }
}
