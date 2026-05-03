package com.example.demo.response.order;

import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.OrderStatus;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class OrderResponse {
    private final Long orderNo;
    private final Long usrNo;
    private final OrderStatus orderStatus;
    private final BigDecimal totalAmount;
    private final LocalDateTime createdAt;

    public OrderResponse(OrderEntity entity) {
        this.orderNo = entity.getOrderNo();
        this.usrNo = entity.getUsrNo();
        this.orderStatus = entity.getOrderStatus();
        this.totalAmount = entity.getTotalAmount();
        this.createdAt = entity.getSysRegDtm();
    }
}
