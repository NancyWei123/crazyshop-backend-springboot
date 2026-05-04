package org.target.product.dto;

import lombok.Data;
import org.target.product.entity.ProductAttribute;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class ProductDetailDTO {

    private Long productId;

    private String name;

    private String imageUrl;

    private BigDecimal price;

    private Integer stock;

    private String longDescription;

    private List<ProductAttribute> attributes;

    private Map<String, Object> specifications;

    private List<String> gallery;
}