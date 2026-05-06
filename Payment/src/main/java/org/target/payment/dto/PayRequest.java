package org.target.payment.dto;

import lombok.Data;

@Data
public class PayRequest {

    private Long orderId;

    private String cardHolderName;

    private String cardNumber;

    private String expiryDate;

    private String cvv;
}