package org.travelsystem.travel.service;

import org.travelsystem.travel.DTO.CartItemDTO;

import java.util.List;

public interface CartService {
    CartItemDTO addToCart(CartItemDTO request);
    List<CartItemDTO> getUserCart(Integer userId);
    void removeFromCart(Long cartItemId);
    CartItemDTO updateCartItem(Long cartItemId, CartItemDTO request);
} 