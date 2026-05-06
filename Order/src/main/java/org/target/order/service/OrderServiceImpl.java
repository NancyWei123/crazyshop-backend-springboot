package org.target.order.service;

import lombok.RequiredArgsConstructor;
import org.apache.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.target.order.dto.OrderDTO;
import org.target.order.dto.OrderSnapshotsMessage;
import org.target.order.dto.ProductCartInfoDTO;
import org.target.order.dto.SubmitOrderRequest;
import org.target.order.entity.Order;
import org.target.order.entity.OrderItem;
import org.target.order.mq.OrderSnapshotProducer;
import org.target.order.repository.OrderItemRepository;
import org.target.order.repository.OrderRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderSnapshotProducer orderSnapshotProducer;
    private final ProductFeignClient productClient;


    // @GlobalTransactional(name = "create-order-tx", rollbackFor = Exception.class)
    @Transactional
    @Override
    public OrderDTO submitOrder(Long userId, SubmitOrderRequest request) {

        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new RuntimeException("Order items cannot be empty");
        }

        Order order = new Order();
        order.setUserId(userId);
        order.setStatus("CREATED");
        order.setCreatedAt(LocalDateTime.now());
        order.setTotalPrice(BigDecimal.ZERO);

        Order savedOrder = orderRepository.save(order);

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> savedItems = new ArrayList<>();

        for (var itemRequest : request.getItems()) {

            if (itemRequest.getProductId() == null) {
                throw new RuntimeException("Product id cannot be null");
            }

            if (itemRequest.getQuantity() == null || itemRequest.getQuantity() <= 0) {
                throw new RuntimeException("Quantity must be greater than 0");
            }

            ProductCartInfoDTO product = productClient.getProductById(itemRequest.getProductId());

            if (product.getStock() < itemRequest.getQuantity()) {
                throw new RuntimeException("Product stock is not enough: " + product.getProductName());
            }

            BigDecimal subtotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(itemRequest.getQuantity()));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder); // important
            orderItem.setProductId(product.getProductId());
            orderItem.setPrice(product.getPrice());
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setSubtotal(subtotal);

            OrderItem savedItem = orderItemRepository.save(orderItem);
            savedItems.add(savedItem);

            totalAmount = totalAmount.add(subtotal);
        }

        savedOrder.setTotalPrice(totalAmount);
        Order finalOrder = orderRepository.save(savedOrder);

        return toDTO(finalOrder, savedItems);
    }

    @Override
    public List<OrderDTO> getMyOrders(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(order -> {
                    List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());
                    return toDTO(order, items);
                })
                .toList();
    }

    @Override
    public OrderDTO getOrdersByUserId(Long userId, Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!Objects.equals(order.getUserId(), userId)) {
            throw new RuntimeException("You cannot access this order");
        }
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setUserId(order.getUserId());
        dto.setStatus(order.getStatus());
        dto.setTotalPrice(order.getTotalPrice());
        return dto;
    }

    @Override
    public OrderDTO markOrderAsPaid(Long userId, Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        if(!order.getId().equals(order.getUserId())) {
            return null;
        }
        order.setStatus("PAID");
        Order saved = orderRepository.save(order);
        OrderDTO dto = new OrderDTO();
        dto.setId(saved.getId());
        dto.setUserId(saved.getUserId());
        dto.setTotalPrice(saved.getTotalPrice());
        dto.setStatus(saved.getStatus());
        dto.setCreatedAt(saved.getCreatedAt());
        return dto;
    }

    private void sendOrderSnapshot(Order order, List<OrderItem> savedItems) {
        OrderSnapshotsMessage message = new OrderSnapshotsMessage();

        message.setOrderId(order.getId());
        message.setUserId(order.getUserId());
        message.setStatus(order.getStatus());
        message.setTotalAmount(order.getTotalPrice());
        message.setCreatedAt(order.getCreatedAt());

        List<OrderSnapshotsMessage.OrderItemSnapshot> snapshotItems =
                savedItems.stream().map(item -> {
                    OrderSnapshotsMessage.OrderItemSnapshot snapshot =
                            new OrderSnapshotsMessage.OrderItemSnapshot();

                    snapshot.setProductId(item.getProductId());
                    snapshot.setPrice(item.getPrice());
                    snapshot.setQuantity(item.getQuantity());
                    snapshot.setSubtotal(item.getSubtotal());

                    return snapshot;
                }).toList();

        message.setItems(snapshotItems);

        orderSnapshotProducer.sendOrderSnapshot(message);
    }

    private OrderDTO toDTO(Order order, List<OrderItem> items) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setUserId(order.getUserId());
        dto.setStatus(order.getStatus());
        dto.setTotalPrice(order.getTotalPrice());
        return dto;
    }
}