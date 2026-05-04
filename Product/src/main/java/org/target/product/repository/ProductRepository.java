package org.target.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.target.product.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByShopId(Long shopId);
}