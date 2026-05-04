package org.target.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.target.order.dto.OrderDTO;
import org.target.order.dto.SubmitOrderRequest;
import org.target.order.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/submit")
    public ResponseEntity<OrderDTO> submitOrder(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody SubmitOrderRequest request
    ) {
        return ResponseEntity.ok(orderService.submitOrder(userId, request));
    }

    @GetMapping("/my")
    public ResponseEntity<List<OrderDTO>> getMyOrders(
            @RequestHeader("X-User-Id") Long userId
    ) {
        return ResponseEntity.ok(orderService.getMyOrders(userId));
    }
}