package com.example.demo.kafka.model;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryReservedEvent {
    private Long orderNo;
    private Long usrNo;
}
