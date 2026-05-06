package org.target.payment.mapper;

import org.target.payment.dto.PaymentDTO;
import org.target.payment.entity.Payment;

public class PaymentMapper {

    public static PaymentDTO toDTO(Payment payment) {
        if (payment == null) {
            return null;
        }

        return PaymentDTO.builder()
                .id(payment.getId())
                .orderId(payment.getOrderId())
                .userId(payment.getUserId())
                .amount(payment.getAmount())
                .paymentMethod(payment.getPaymentMethod())
                .status(payment.getStatus())
                .transactionId(payment.getTransactionId())
                .createdAt(payment.getCreatedAt())
                .paidAt(payment.getPaidAt())
                .refundedAt(payment.getRefundedAt())
                .build();
    }
}