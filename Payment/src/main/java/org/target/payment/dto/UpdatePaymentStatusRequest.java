package org.target.payment.dto;

import lombok.Data;

@Data
public class UpdatePaymentStatusRequest {

    private String status;

    private String transactionId;
}