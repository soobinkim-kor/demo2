package com.example.demo.controller;

import com.example.demo.request.order.OrderCreateRequest;
import com.example.demo.response.order.OrderResponse;
import com.example.demo.service.MonoOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders/mono")
@RequiredArgsConstructor
public class MonoOrderController {

    private final MonoOrderService monoOrderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderCreateRequest request) {
        return ResponseEntity.ok(monoOrderService.createOrder(request));
    }

    @PostMapping("/init")
    public ResponseEntity<String> initTestInventory() {
        monoOrderService.initTestInventory();
        return ResponseEntity.ok("재고 초기화 완료 (상품 1~10, 각 100만개)");
    }
}
