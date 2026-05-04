package org.target.payment.service;

import org.target.payment.dto.CreatePaymentRequest;
import org.target.payment.dto.UpdatePaymentStatusRequest;
import org.target.payment.entity.Payment;

import java.util.List;

public interface PaymentService {

    Payment createPayment(Long userId, CreatePaymentRequest request);

    Payment getPaymentById(Long paymentId);

    List<Payment> getPaymentsByOrderId(Long orderId);

    List<Payment> getMyPayments(Long userId);

    Payment updatePaymentStatus(Long paymentId, UpdatePaymentStatusRequest request);
}