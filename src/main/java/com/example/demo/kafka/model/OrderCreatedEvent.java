package com.example.demo.kafka.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {

    private Long orderNo;
    private Long usrNo;
    private BigDecimal totalAmount;
    private List<OrderItemPayload> items;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemPayload {
        private Long productNo;
        private int quantity;
        private BigDecimal unitPrice;
    }
}
