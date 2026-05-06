package org.target.payment.service;

import org.target.payment.dto.PaymentDTO;
import org.target.payment.dto.PaymentRequest;

public interface PaymentService {

    PaymentDTO pay(Long userId, PaymentRequest request);

    PaymentDTO getPaymentByOrderId(Long userId, Long orderId);

    PaymentDTO refund(Long userId, Long paymentId);
}