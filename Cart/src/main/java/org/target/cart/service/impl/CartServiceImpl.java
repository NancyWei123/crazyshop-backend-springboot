package org.target.cart.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.target.cart.dto.AddCartItemRequest;
import org.target.cart.dto.CartDTO;
import org.target.cart.dto.ProductCartInfoDTO;
import org.target.cart.dto.UpdateCartItemRequest;
import org.target.cart.entity.CartItem;
import org.target.cart.mapper.CartMapper;
import org.target.cart.repository.CartRepository;
import org.target.cart.service.CartService;
import org.target.cart.service.ProductFeignClient;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductFeignClient productFeignClient;

    @Override
    @Transactional
    public CartDTO addToCart(Long userId, AddCartItemRequest request) {
        ProductCartInfoDTO product =
                productFeignClient.getProductCartInfo(request.getProductId());

        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }

        if (product.getStock() < request.getQuantity()) {
            throw new RuntimeException("Product stock is not enough");
        }

        CartItem cartItem = cartRepository
                .findByUserIdAndProductId(userId, request.getProductId())
                .orElse(null);

        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(product.getProductId());
            cartItem.setProductName(product.getProductName());
            cartItem.setProductImage(product.getProductImage());
            cartItem.setPrice(product.getPrice());
            cartItem.setQuantity(request.getQuantity());
            cartItem.setCreatedAt(LocalDateTime.now());
        } else {
            int newQuantity = cartItem.getQuantity() + request.getQuantity();

            if (product.getStock() < newQuantity) {
                throw new RuntimeException("Product stock is not enough");
            }

            cartItem.setQuantity(newQuantity);
        }

        cartItem.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cartItem);

        return getMyCart(userId);
    }

    @Override
    public CartDTO getMyCart(Long userId) {
        List<CartItem> items = cartRepository.findByUserId(userId);
        return cartMapper.toCartDTO(userId, items);
    }

    @Override
    @Transactional
    public CartDTO updateCartItem(Long userId, Long cartItemId, UpdateCartItemRequest request) {
        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }

        CartItem cartItem = cartRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!cartItem.getUserId().equals(userId)) {
            throw new RuntimeException("You cannot update this cart item");
        }

        ProductCartInfoDTO product =
                productFeignClient.getProductCartInfo(cartItem.getProductId());

        if (product.getStock() < request.getQuantity()) {
            throw new RuntimeException("Product stock is not enough");
        }

        cartItem.setQuantity(request.getQuantity());
        cartItem.setUpdatedAt(LocalDateTime.now());

        cartRepository.save(cartItem);

        return getMyCart(userId);
    }

    @Override
    @Transactional
    public void removeCartItem(Long userId, Long cartItemId) {
        CartItem cartItem = cartRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!cartItem.getUserId().equals(userId)) {
            throw new RuntimeException("You cannot remove this cart item");
        }

        cartRepository.delete(cartItem);
    }

    @Override
    @Transactional
    public void clearCart(Long userId) {
        cartRepository.deleteByUserId(userId);
    }
}