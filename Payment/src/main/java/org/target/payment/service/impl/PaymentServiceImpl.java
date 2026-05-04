package org.target.payment.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.target.payment.dto.CreatePaymentRequest;
import org.target.payment.dto.UpdatePaymentStatusRequest;
import org.target.payment.entity.Payment;
import org.target.payment.mapper.PaymentMapper;
import org.target.payment.repository.PaymentRepository;
import org.target.payment.service.PaymentService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public Payment createPayment(Long userId, CreatePaymentRequest request) {
        Payment payment = paymentMapper.toEntity(userId, request);
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + paymentId));
    }

    @Override
    public List<Payment> getPaymentsByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId);
    }

    @Override
    public List<Payment> getMyPayments(Long userId) {
        return paymentRepository.findByUserId(userId);
    }

    @Override
    public Payment updatePaymentStatus(Long paymentId, UpdatePaymentStatusRequest request) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + paymentId));

        payment.setStatus(request.getStatus());
        payment.setTransactionId(request.getTransactionId());
        payment.setUpdatedAt(LocalDateTime.now());

        if ("PAID".equalsIgnoreCase(request.getStatus())) {
            payment.setPaidAt(LocalDateTime.now());
        }

        return paymentRepository.save(payment);
    }
}