package com.example.demo.kafka.model;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryRestoreEvent {
    private Long orderNo;
    private Long usrNo;
    private List<OrderCreatedEvent.OrderItemPayload> items;
}
