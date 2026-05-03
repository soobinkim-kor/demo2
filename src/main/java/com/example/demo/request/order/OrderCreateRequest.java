package com.example.demo.request.order;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderCreateRequest {
    private Long usrNo;
    private List<OrderItemRequest> items;

    @Getter
    @NoArgsConstructor
    public static class OrderItemRequest {
        private Long productNo;
        private String productNm;
        private int quantity;
        private BigDecimal unitPrice;
    }
}
