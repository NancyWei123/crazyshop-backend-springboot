package org.target.order.service;

import org.target.order.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.target.order.dto.ProductOrderInfoDTO;

@FeignClient(name = "product-service")
public interface ProductFeignClient {

    @GetMapping("/api/products/{id}")
    ProductOrderInfoDTO getProductById(@PathVariable("id") Long id);
}