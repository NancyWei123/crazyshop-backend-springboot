package org.target.order.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductOrderInfoDTO {

    private Long productId;

    private String productName;

    private String imageUrl;

    private BigDecimal price;

    private Integer stock;
}