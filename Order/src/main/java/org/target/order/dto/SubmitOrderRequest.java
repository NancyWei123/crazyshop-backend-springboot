package org.target.order.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SubmitOrderRequest {

    private List<OrderItemRequest> items;

    @Data
    public static class OrderItemRequest {
        private Long productId;
        private Integer quantity;
    }
}