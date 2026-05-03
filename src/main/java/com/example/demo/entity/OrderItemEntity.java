package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ORDER_ITEM")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ITEM_NO")
    private Long orderItemNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_NO", nullable = false)
    private OrderEntity order;

    @Column(name = "PRODUCT_NO", nullable = false)
    private Long productNo;

    @Column(name = "PRODUCT_NM", nullable = false)
    private String productNm;

    @Column(name = "QUANTITY", nullable = false)
    private int quantity;

    @Column(name = "UNIT_PRICE", nullable = false)
    private BigDecimal unitPrice;
}
