package org.target.cart.service;

import org.target.cart.dto.AddCartItemRequest;
import org.target.cart.dto.CartDTO;
import org.target.cart.dto.UpdateCartItemRequest;

public interface CartService {

    CartDTO addToCart(Long userId, AddCartItemRequest request);

    CartDTO getMyCart(Long userId);

    CartDTO updateCartItem(Long userId, Long cartItemId, UpdateCartItemRequest request);

    void removeCartItem(Long userId, Long cartItemId);

    void clearCart(Long userId);
}