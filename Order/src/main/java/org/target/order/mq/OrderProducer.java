package org.target.order.mq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.target.order.config.RabbitConfig;
import org.target.order.dto.OrderMessage;

@Component
@RequiredArgsConstructor
public class OrderProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendOrderMessage(OrderMessage message) {
        rabbitTemplate.convertAndSend(RabbitConfig.ORDER_QUEUE, message);
    }
}