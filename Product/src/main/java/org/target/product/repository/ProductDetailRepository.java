package org.target.product.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.target.product.entity.ProductDetail;

import java.util.Optional;

public interface ProductDetailRepository extends MongoRepository<ProductDetail, String> {

    Optional<ProductDetail> findByProductId(Long productId);
}