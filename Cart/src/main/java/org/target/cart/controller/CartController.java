package org.target.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.target.cart.dto.AddCartItemRequest;
import org.target.cart.dto.CartDTO;
import org.target.cart.dto.UpdateCartItemRequest;
import org.target.cart.service.CartService;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public CartDTO addToCart(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody AddCartItemRequest request
    ) {
        return cartService.addToCart(userId, request);
    }

    @GetMapping("/my")
    public CartDTO getMyCart(
            @RequestHeader("X-User-Id") Long userId
    ) {
        return cartService.getMyCart(userId);
    }

    @PutMapping("/{cartItemId}")
    public CartDTO updateCartItem(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long cartItemId,
            @RequestBody UpdateCartItemRequest request
    ) {
        return cartService.updateCartItem(userId, cartItemId, request);
    }

    @DeleteMapping("/{cartItemId}")
    public void removeCartItem(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long cartItemId
    ) {
        cartService.removeCartItem(userId, cartItemId);
    }

    @DeleteMapping("/clear")
    public void clearCart(
            @RequestHeader("X-User-Id") Long userId
    ) {
        cartService.clearCart(userId);
    }
}