package com.example.demo.controller;

import com.example.demo.request.order.OrderCreateRequest;
import com.example.demo.response.order.OrderResponse;
import com.example.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderCreateRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @GetMapping("/{orderNo}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long orderNo) {
        return ResponseEntity.ok(orderService.getOrder(orderNo));
    }
}
