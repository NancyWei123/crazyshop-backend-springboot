package org.target.product.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@Document(collection = "product_details")
public class ProductDetail {

    @Id
    private String id;

    private Long productId;

    private String longDescription;

    private List<ProductAttribute> attributes;

    private ProductSpecifications specifications;

    private List<String> gallery;

    private Instant createdAt;

    private Instant updatedAt;
}