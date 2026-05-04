package org.target.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.target.order.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}