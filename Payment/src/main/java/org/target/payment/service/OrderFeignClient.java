package org.target.payment.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.target.payment.dto.OrderDTO;

@FeignClient(name = "order-service")
public interface OrderFeignClient {
    @GetMapping("/api/orders/{id}")
    OrderDTO getOrderById(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable("id") Long id
    );

    void markOrderAsPaid(Long userId, Long id);

}
