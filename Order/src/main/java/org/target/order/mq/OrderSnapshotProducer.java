package org.target.order.mq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.target.order.config.RabbitConfig;
import org.target.order.dto.OrderSnapshotsMessage;

@Component
@RequiredArgsConstructor
public class OrderSnapshotProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendOrderSnapshot(OrderSnapshotsMessage message) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.ORDER_SNAPSHOT_QUEUE,
                message
        );
    }
}