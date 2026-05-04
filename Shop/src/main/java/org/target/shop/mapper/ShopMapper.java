package org.target.shop.mapper;

import org.springframework.stereotype.Component;
import org.target.shop.dto.CreateShopRequest;
import org.target.shop.entity.Shop;

import java.time.LocalDateTime;

@Component
public class ShopMapper {

    public Shop toEntity(Long ownerUserId, CreateShopRequest request) {
        LocalDateTime now = LocalDateTime.now();

        Shop shop = new Shop();
        shop.setOwnerUserId(ownerUserId);
        shop.setName(request.getName());
        shop.setDescription(request.getDescription());
        shop.setLogoUrl(request.getLogoUrl());
        shop.setStatus("PENDING");
        shop.setCreatedAt(now);
        shop.setUpdatedAt(now);

        return shop;
    }
}