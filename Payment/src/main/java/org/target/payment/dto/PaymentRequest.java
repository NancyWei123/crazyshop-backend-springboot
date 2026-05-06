package org.target.payment.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {

    private Long orderId;

    private BigDecimal amount;

    private String cardHolderName;

    private String cardNumber;

    private String expiryDate;

    private String cvv;
}