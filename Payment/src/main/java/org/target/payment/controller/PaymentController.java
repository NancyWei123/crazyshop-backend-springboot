package org.target.payment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.target.payment.dto.CreatePaymentRequest;
import org.target.payment.dto.UpdatePaymentStatusRequest;
import org.target.payment.entity.Payment;
import org.target.payment.service.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public Payment createPayment(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody CreatePaymentRequest request
    ) {
        return paymentService.createPayment(userId, request);
    }

    @GetMapping("/{paymentId}")
    public Payment getPaymentById(@PathVariable Long paymentId) {
        return paymentService.getPaymentById(paymentId);
    }

    @GetMapping("/order/{orderId}")
    public List<Payment> getPaymentsByOrderId(@PathVariable Long orderId) {
        return paymentService.getPaymentsByOrderId(orderId);
    }

    @GetMapping("/my")
    public List<Payment> getMyPayments(
            @RequestHeader("X-User-Id") Long userId
    ) {
        return paymentService.getMyPayments(userId);
    }

    @PutMapping("/{paymentId}/status")
    public Payment updatePaymentStatus(
            @PathVariable Long paymentId,
            @RequestBody UpdatePaymentStatusRequest request
    ) {
        return paymentService.updatePaymentStatus(paymentId, request);
    }
}