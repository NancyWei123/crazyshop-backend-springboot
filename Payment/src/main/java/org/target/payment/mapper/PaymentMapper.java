package org.target.payment.mapper;

import org.springframework.stereotype.Component;
import org.target.payment.dto.CreatePaymentRequest;
import org.target.payment.entity.Payment;

import java.time.LocalDateTime;

@Component
public class PaymentMapper {

    public Payment toEntity(Long userId, CreatePaymentRequest request) {
        LocalDateTime now = LocalDateTime.now();

        Payment payment = new Payment();
        payment.setUserId(userId);
        payment.setOrderId(request.getOrderId());
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setStatus("PENDING");
        payment.setCreatedAt(now);
        payment.setUpdatedAt(now);

        return payment;
    }
}