package com.example.demo.entity;

public enum OrderStatus {
    PENDING,         // 주문 접수
    RESERVED,        // 재고 예약 완료
    CONFIRMED,       // 결제 완료 / 주문 확정
    PAYMENT_FAILED,  // 결제 실패 (재고 복구 트리거)
    CANCELLED,       // 취소 (재고 부족)
    FAILED           // 처리 중 오류
}
