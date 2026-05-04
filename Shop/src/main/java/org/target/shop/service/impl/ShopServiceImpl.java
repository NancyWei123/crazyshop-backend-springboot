package org.target.shop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.target.shop.dto.CreateShopRequest;
import org.target.shop.entity.Shop;
import org.target.shop.mapper.ShopMapper;
import org.target.shop.repository.ShopRepository;
import org.target.shop.service.ShopService;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;

    @Override
    public Shop createShop(Long ownerUserId, CreateShopRequest request) {
        if (shopRepository.existsByOwnerUserId(ownerUserId)) {
            throw new RuntimeException("This user already has a shop");
        }

        Shop shop = shopMapper.toEntity(ownerUserId, request);

        return shopRepository.save(shop);
    }

    @Override
    public Shop getMyShop(Long ownerUserId) {
        return shopRepository.findByOwnerUserId(ownerUserId)
                .orElseThrow(() -> new RuntimeException("Shop not found for user id: " + ownerUserId));
    }

    @Override
    public Shop getShopById(Long shopId) {
        return shopRepository.findById(shopId)
                .orElseThrow(() -> new RuntimeException("Shop not found with id: " + shopId));
    }
}