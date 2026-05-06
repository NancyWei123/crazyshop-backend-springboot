package org.target.order.service;

import org.target.order.dto.OrderDTO;
import org.target.order.dto.SubmitOrderRequest;

import java.util.List;

public interface OrderService {
    OrderDTO submitOrder(Long userId, SubmitOrderRequest request);

    List<OrderDTO> getMyOrders(Long userId);

    OrderDTO getOrdersByUserId(Long userId, Long id);

    OrderDTO markOrderAsPaid(Long userId, Long id);
}