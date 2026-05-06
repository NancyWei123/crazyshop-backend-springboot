package org.target.payment.dto;

import lombok.Builder;
import lombok.Data;
import org.target.payment.entity.PaymentMethod;
import org.target.payment.entity.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PaymentDTO {

    private Long id;

    private Long orderId;

    private Long userId;

    private BigDecimal amount;

    private PaymentMethod paymentMethod;

    private PaymentStatus status;

    private String transactionId;

    private LocalDateTime createdAt;

    private LocalDateTime paidAt;

    private LocalDateTime refundedAt;
}