package org.target.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.target.shop.dto.CreateShopRequest;
import org.target.shop.entity.Shop;
import org.target.shop.service.ShopService;

@RestController
@RequestMapping("/api/shops")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @PostMapping
    public Shop createShop(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody CreateShopRequest request
    ) {
        return shopService.createShop(userId, request);
    }

    @GetMapping("/my")
    public Shop getMyShop(
            @RequestHeader("X-User-Id") Long userId
    ) {
        return shopService.getMyShop(userId);
    }

    @GetMapping("/{shopId}")
    public Shop getShopById(@PathVariable Long shopId) {
        return shopService.getShopById(shopId);
    }
}