package org.target.order.mq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.target.order.config.RabbitConfig;
import org.target.order.dto.OrderSnapshotsMessage;
import org.target.order.entity.OrderSnapshotDocument;
import org.target.order.repository.OrderSnapshotRepository;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderSnapshotConsumer {

    private final OrderSnapshotRepository orderSnapshotRepository;

    @RabbitListener(queues = RabbitConfig.ORDER_SNAPSHOT_QUEUE)
    public void consume(OrderSnapshotsMessage message) {

        OrderSnapshotDocument document = new OrderSnapshotDocument();

        document.setOrderId(message.getOrderId());
        document.setUserId(message.getUserId());
        document.setShopId(message.getShopId());
        document.setStatus(message.getStatus());
        document.setTotalAmount(message.getTotalAmount());
        document.setCreatedAt(message.getCreatedAt());
        document.setSavedAt(LocalDateTime.now());

        List<OrderSnapshotDocument.OrderItemSnapshotDocument> itemDocuments =
                message.getItems().stream().map(item -> {
                    OrderSnapshotDocument.OrderItemSnapshotDocument itemDoc =
                            new OrderSnapshotDocument.OrderItemSnapshotDocument();

                    itemDoc.setProductId(item.getProductId());
                    itemDoc.setProductName(item.getProductName());
                    itemDoc.setImageUrl(item.getImageUrl());
                    itemDoc.setPrice(item.getPrice());
                    itemDoc.setQuantity(item.getQuantity());
                    itemDoc.setSubtotal(item.getSubtotal());

                    return itemDoc;
                }).toList();

        document.setItems(itemDocuments);

        orderSnapshotRepository.save(document);

        System.out.println("Saved order snapshot to MongoDB, orderId = " + message.getOrderId());
    }
}