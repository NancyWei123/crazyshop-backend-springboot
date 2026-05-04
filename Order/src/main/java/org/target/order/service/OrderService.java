package org.target.order.service;

import org.target.order.dto.OrderDTO;
import org.target.order.dto.SubmitOrderRequest;

import java.util.List;

public interface OrderService {
    OrderDTO submitOrder(Long userId, SubmitOrderRequest request);
    List<OrderDTO> getMyOrders(Long userId);
}