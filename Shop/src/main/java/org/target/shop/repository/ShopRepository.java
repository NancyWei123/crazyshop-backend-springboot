package org.target.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.target.shop.entity.Shop;

import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, Long> {

    Optional<Shop> findByOwnerUserId(Long ownerUserId);

    boolean existsByOwnerUserId(Long ownerUserId);
}