package org.target.order.service;

import org.target.order.dto.ProductCartInfoDTO;
import org.target.order.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "product-service")
public interface ProductFeignClient {

    @GetMapping("/api/products/{id}/cart-info")
    ProductCartInfoDTO getProductById(@PathVariable("id") Long id);
}