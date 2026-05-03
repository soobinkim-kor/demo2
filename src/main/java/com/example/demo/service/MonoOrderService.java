package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.order.MonoInventoryRepository;
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
public class MonoOrderService {

    private final OrderRepository orderRepository;
    private final MonoInventoryRepository monoInventoryRepository;

    /**
     * 재고 차감 + 주문 생성을 단일 DB 트랜잭션으로 처리 (모놀리식 방식)
     */
    @Transactional
    public OrderResponse createOrder(OrderCreateRequest request) {
        // 1. 재고 확인 및 차감 (비관적 락)
        for (OrderCreateRequest.OrderItemRequest item : request.getItems()) {
            MonoInventoryEntity inventory = monoInventoryRepository
                    .findByProductNoWithLock(item.getProductNo())
                    .orElseThrow(() -> new IllegalStateException("재고 없음: productNo=" + item.getProductNo()));
            inventory.deduct(item.getQuantity());
            monoInventoryRepository.save(inventory);
        }

        // 2. 주문 생성
        BigDecimal totalAmount = request.getItems().stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        OrderEntity order = OrderEntity.builder()
                .usrNo(request.getUsrNo())
                .orderStatus(OrderStatus.CONFIRMED)
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

        return new OrderResponse(saved);
    }

    /**
     * 스트레스 테스트용 초기 재고 세팅
     */
    @Transactional
    public void initTestInventory() {
        monoInventoryRepository.deleteAll();
        for (long i = 1; i <= 10; i++) {
            monoInventoryRepository.save(MonoInventoryEntity.builder()
                    .productNo(i)
                    .productNm("상품-" + i)
                    .stockQty(1_000_000)
                    .build());
        }
        log.info("Test inventory initialized: product 1~10, stock 1,000,000 each");
    }
}
