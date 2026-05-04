package org.target.order.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "order_snapshots")
public class OrderSnapshotDocument {

    @Id
    private String id;

    private Long orderId;
    private Long userId;
    private Long shopId;

    private String status;
    private BigDecimal totalAmount;

    private List<OrderItemSnapshotDocument> items;

    private LocalDateTime createdAt;
    private LocalDateTime savedAt;

    @Data
    public static class OrderItemSnapshotDocument {
        private Long productId;
        private String productName;
        private String imageUrl;
        private BigDecimal price;
        private Integer quantity;
        private BigDecimal subtotal;
    }
}