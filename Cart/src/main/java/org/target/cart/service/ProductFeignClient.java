package org.target.cart.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.target.cart.dto.ProductCartInfoDTO;

@FeignClient(name = "product-service", url = "http://localhost:8082")
public interface ProductFeignClient {

    @GetMapping("/api/products/{productId}/cart-info")
    ProductCartInfoDTO getProductCartInfo(@PathVariable Long productId);
}