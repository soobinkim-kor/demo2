package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "MONO_INVENTORY")
public class MonoInventoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INVENTORY_NO")
    private Long inventoryNo;

    @Column(name = "PRODUCT_NO", nullable = false, unique = true)
    private Long productNo;

    @Column(name = "PRODUCT_NM", nullable = false)
    private String productNm;

    @Column(name = "STOCK_QTY", nullable = false)
    private int stockQty;

    @Column(name = "SYS_UPD_DTM")
    @LastModifiedDate
    private LocalDateTime sysUpdDtm;

    public void deduct(int quantity) {
        if (this.stockQty < quantity) {
            throw new IllegalStateException("재고 부족: productNo=" + productNo + ", 재고=" + stockQty + ", 요청=" + quantity);
        }
        this.stockQty -= quantity;
    }
}
