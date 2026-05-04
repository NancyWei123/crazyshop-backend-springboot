package org.target.cart.mapper;

import org.springframework.stereotype.Component;
import org.target.cart.dto.CartDTO;
import org.target.cart.dto.CartItemDTO;
import org.target.cart.entity.CartItem;

import java.math.BigDecimal;
import java.util.List;

@Component
public class CartMapper {

    public CartItemDTO toItemDTO(CartItem item) {
        CartItemDTO dto = new CartItemDTO();

        dto.setId(item.getId());
        dto.setProductId(item.getProductId());
        dto.setProductName(item.getProductName());
        dto.setProductImage(item.getProductImage());
        dto.setPrice(item.getPrice());
        dto.setQuantity(item.getQuantity());

        BigDecimal subtotal = item.getPrice()
                .multiply(BigDecimal.valueOf(item.getQuantity()));

        dto.setSubtotal(subtotal);

        return dto;
    }

    public CartDTO toCartDTO(Long userId, List<CartItem> items) {
        CartDTO dto = new CartDTO();

        List<CartItemDTO> itemDTOs = items.stream()
                .map(this::toItemDTO)
                .toList();

        BigDecimal totalPrice = itemDTOs.stream()
                .map(CartItemDTO::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        dto.setUserId(userId);
        dto.setItems(itemDTOs);
        dto.setTotalPrice(totalPrice);

        return dto;
    }
}