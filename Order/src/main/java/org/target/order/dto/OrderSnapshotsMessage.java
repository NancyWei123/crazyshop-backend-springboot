package org.target.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSnapshotsMessage {

    private Long orderId;
    private Long userId;
    private Long shopId;

    private String status;
    private BigDecimal totalAmount;

    private List<OrderItemSnapshot> items;

    private LocalDateTime createdAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemSnapshot {
        private Long productId;
        private String productName;
        private String imageUrl;
        private BigDecimal price;
        private Integer quantity;
        private BigDecimal subtotal;
    }
}