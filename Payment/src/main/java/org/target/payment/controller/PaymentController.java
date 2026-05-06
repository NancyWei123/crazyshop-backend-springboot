package org.target.payment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.target.payment.dto.PaymentDTO;
import org.target.payment.dto.PaymentRequest;
import org.target.payment.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/pay")
    public PaymentDTO pay(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody PaymentRequest request
    ) {
        return paymentService.pay(userId, request);
    }

    @GetMapping("/order/{orderId}")
    public PaymentDTO getPaymentByOrderId(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long orderId
    ) {
        return paymentService.getPaymentByOrderId(userId, orderId);
    }

    @PutMapping("/{paymentId}/refund")
    public PaymentDTO refund(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long paymentId
    ) {
        return paymentService.refund(userId, paymentId);
    }
}