package org.target.payment.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreatePaymentRequest {

    private Long orderId;

    private BigDecimal amount;

    private String paymentMethod;
}