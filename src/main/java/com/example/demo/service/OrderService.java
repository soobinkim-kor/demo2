package com.example.demo.service;

import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.OrderItemEntity;
import com.example.demo.entity.OrderStatus;
import com.example.demo.global.error.BusinessException;
import com.example.demo.global.error.CommonErrorCode;
import com.example.demo.kafka.model.OrderCreatedEvent;
import com.example.demo.kafka.model.InventoryFailedEvent;
import com.example.demo.kafka.model.InventoryReservedEvent;
import com.example.demo.kafka.producer.OrderEventProducer;
import com.example.demo.repository.order.OrderRepository;
import com.example.demo.request.order.OrderCreateRequest;
import com.example.demo.response.order.OrderResponse;
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
    private final OrderEventProducer orderEventProducer;

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

        orderEventProducer.sendOrderCreated(event);
        log.info("Order created and event published: orderNo={}", saved.getOrderNo());

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
        orderRepository.save(order);

        // 결제 처리 (간단 구현: 바로 CONFIRMED)
        processPayment(order);
    }

    @Transactional
    public void handleInventoryFailed(InventoryFailedEvent event) {
        OrderEntity order = orderRepository.findById(event.getOrderNo())
                .orElseThrow(() -> new BusinessException(CommonErrorCode.RESOURCE_NOT_FOUND));

        order.updateStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        log.warn("Order cancelled due to inventory failure: orderNo={}, reason={}", event.getOrderNo(), event.getReason());
    }

    private void processPayment(OrderEntity order) {
        // 실제 결제 로직은 추후 PG 연동
        order.updateStatus(OrderStatus.CONFIRMED);
        orderRepository.save(order);
        log.info("Payment processed and order confirmed: orderNo={}", order.getOrderNo());
    }
}
