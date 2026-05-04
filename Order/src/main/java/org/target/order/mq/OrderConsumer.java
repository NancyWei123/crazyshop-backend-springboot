package org.target.order.mq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.target.order.config.RabbitConfig;
import org.target.order.dto.OrderMessage;
import org.target.order.entity.Order;
import org.target.order.repository.OrderRepository;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class OrderConsumer {

    private final OrderRepository orderRepository;

    @RabbitListener(queues = RabbitConfig.ORDER_QUEUE)
    public void receiveOrderMessage(OrderMessage message) {
        Order order = Order.builder()
                .userId(message.getUserId())
                .totalPrice(message.getTotalPrice())
                .status("PENDING_PAYMENT")
                .createdAt(LocalDateTime.now())
                .build();

        orderRepository.save(order);
    }
}