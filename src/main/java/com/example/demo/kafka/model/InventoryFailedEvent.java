package com.example.demo.kafka.model;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryFailedEvent {
    private Long orderNo;
    private Long usrNo;
    private String reason;
}
