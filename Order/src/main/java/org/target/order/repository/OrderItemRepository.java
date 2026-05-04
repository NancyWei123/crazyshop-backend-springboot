package org.target.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.target.order.entity.OrderItem;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrderId(Long orderId);
}