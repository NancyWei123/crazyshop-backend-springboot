package org.target.cart.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductCartInfoDTO {

    private Long productId;

    private String productName;

    private String productImage;

    private BigDecimal price;

    private Integer stock;
}