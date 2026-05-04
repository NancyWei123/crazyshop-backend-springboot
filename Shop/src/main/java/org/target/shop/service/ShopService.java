package org.target.shop.service;

import org.target.shop.dto.CreateShopRequest;
import org.target.shop.entity.Shop;

public interface ShopService {

    Shop createShop(Long ownerUserId, CreateShopRequest request);

    Shop getMyShop(Long ownerUserId);

    Shop getShopById(Long shopId);
}