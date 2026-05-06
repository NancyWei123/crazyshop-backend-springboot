package org.target.payment.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.target.payment.dto.OrderDTO;
import org.target.payment.dto.PaymentDTO;
import org.target.payment.dto.PaymentRequest;
import org.target.payment.entity.Payment;
import org.target.payment.entity.PaymentMethod;
import org.target.payment.entity.PaymentStatus;
import org.target.payment.service.OrderFeignClient;
import org.target.payment.mapper.PaymentMapper;
import org.target.payment.repository.PaymentRepository;
import org.target.payment.service.PaymentService;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderFeignClient orderFeignClient;

    @Override
    public PaymentDTO pay(Long userId, PaymentRequest request) {
        validatePaymentRequest(request);

        OrderDTO order = orderFeignClient.getOrderById(userId, request.getOrderId());

        if (order == null) {
            throw new RuntimeException("Order not found");
        }

        if (!order.getUserId().equals(userId)) {
            throw new RuntimeException("You cannot pay another user's order");
        }

        if ("PAID".equalsIgnoreCase(order.getStatus())) {
            throw new RuntimeException("Order already paid");
        }

        if (paymentRepository.findByOrderId(request.getOrderId()).isPresent()) {
            throw new RuntimeException("Payment already exists for this order");
        }

        Payment payment = Payment.builder()
                .orderId(order.getId())
                .userId(userId)
                .amount(order.getTotalPrice())
                .paymentMethod(PaymentMethod.WESTPAC_CARD)
                .status(PaymentStatus.SUCCESS)
                .transactionId("WESTPAC-MOCK-" + UUID.randomUUID())
                .createdAt(LocalDateTime.now())
                .paidAt(LocalDateTime.now())
                .build();

        Payment saved = paymentRepository.save(payment);

        orderFeignClient.markOrderAsPaid(userId, order.getId());

        return PaymentMapper.toDTO(saved);
    }

    @Override
    public PaymentDTO getPaymentByOrderId(Long userId, Long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (!payment.getUserId().equals(userId)) {
            throw new RuntimeException("You cannot view another user's payment");
        }

        return PaymentMapper.toDTO(payment);
    }

    @Override
    public PaymentDTO refund(Long userId, Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (!payment.getUserId().equals(userId)) {
            throw new RuntimeException("You cannot refund another user's payment");
        }

        if (payment.getStatus() != PaymentStatus.SUCCESS) {
            throw new RuntimeException("Only successful payment can be refunded");
        }

        payment.setStatus(PaymentStatus.REFUNDED);
        payment.setRefundedAt(LocalDateTime.now());

        Payment saved = paymentRepository.save(payment);

        return PaymentMapper.toDTO(saved);
    }

    private void validatePaymentRequest(PaymentRequest request) {
        if (request.getOrderId() == null) {
            throw new RuntimeException("Order ID is required");
        }

        if (request.getCardHolderName() == null || request.getCardHolderName().isBlank()) {
            throw new RuntimeException("Card holder name is required");
        }

        if (request.getCardNumber() == null || request.getCardNumber().replace(" ", "").length() < 12) {
            throw new RuntimeException("Invalid card number");
        }

        if (request.getExpiryDate() == null || request.getExpiryDate().isBlank()) {
            throw new RuntimeException("Expiry date is required");
        }

        if (request.getCvv() == null || request.getCvv().length() < 3) {
            throw new RuntimeException("Invalid CVV");
        }
    }
}