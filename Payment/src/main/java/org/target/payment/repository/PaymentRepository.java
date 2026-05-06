package org.target.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.target.payment.entity.Payment;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByOrderId(Long orderId);
}