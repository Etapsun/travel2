package org.travelsystem.travel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.travelsystem.travel.DTO.CartItemDTO;
import org.travelsystem.travel.service.CartService;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "购物车管理", description = "购物车相关接口")
public class CartController {
    private final CartService cartService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "添加到购物车", description = "添加商品到购物车")
    public CartItemDTO addToCart(@Valid @RequestBody CartItemDTO request) {
        return cartService.addToCart(request);
    }
    
    @GetMapping("/user/{userId}")
    @Operation(summary = "获取用户购物车", description = "获取指定用户的购物车内容")
    public List<CartItemDTO> getUserCart(@PathVariable Integer userId) {
        return cartService.getUserCart(userId);
    }
    
    @DeleteMapping("/{cartItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "从购物车移除", description = "从购物车中移除商品")
    public void removeFromCart(@PathVariable Long cartItemId) {
        cartService.removeFromCart(cartItemId);
    }
    
    @PutMapping("/{cartItemId}")
    @Operation(summary = "更新购物车项", description = "更新购物车中的商品数量")
    public CartItemDTO updateCartItem(
            @PathVariable Long cartItemId, 
            @Valid @RequestBody CartItemDTO request) {
        return cartService.updateCartItem(cartItemId, request);
    }
} 