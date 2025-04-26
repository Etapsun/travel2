package org.travelsystem.travel.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.travelsystem.travel.DTO.CartItemDTO;
import org.travelsystem.travel.entity.PaymentStatus;
import org.travelsystem.travel.entity.ProductInformation;
import org.travelsystem.travel.entity.ShoppingCart;
import org.travelsystem.travel.respository.ProductRepository;
import org.travelsystem.travel.respository.ShoppingCartRepository;
import org.travelsystem.travel.service.CartService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;
    
    @Override
    @Transactional
    public CartItemDTO addToCart(CartItemDTO request) {
        // 默认设置为已选中
        if(request.getSelected() == null) {
            request.setSelected(true);
        }
        
        // 把商品ID转为Long类型，防止类型转换错误
        Long productId = request.getProductId().longValue();
        
        // 检查商品是否存在并获取详情
        ProductInformation product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("商品不存在，ID: " + productId));
        
        // 创建购物车实体
        ShoppingCart cartItem = ShoppingCart.builder()
                .userId(request.getUserId())
                .productId(request.getProductId())
                .productName(product.getProductDescription()) // 使用商品实际名称
                .productType(product.getProductType().toString())
                .paymentAmount(product.getPrice())
                .contactPerson(request.getContactPerson())
                .contactNumber(request.getContactNumber())
                .idCardNumber(request.getIdCardNumber())
                .paymentStatus(PaymentStatus.未付款) // 使用枚举值
                .creationTime(LocalDateTime.now())
                .couponBool(false) // 默认不使用优惠券
                .build();
        
        // 保存到数据库
        ShoppingCart saved = shoppingCartRepository.save(cartItem);
        
        // 转换为DTO并返回
        return convertToDTO(saved);
    }
    
    @Override
    public List<CartItemDTO> getUserCart(Integer userId) {
        return shoppingCartRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public void removeFromCart(Long cartItemId) {
        shoppingCartRepository.deleteById(cartItemId);
    }
    
    @Override
    @Transactional
    public CartItemDTO updateCartItem(Long cartItemId, CartItemDTO request) {
        ShoppingCart cartItem = shoppingCartRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("购物车项不存在，ID: " + cartItemId));
        
        // 更新联系信息
        if (request.getContactPerson() != null) {
            cartItem.setContactPerson(request.getContactPerson());
        }
        
        if (request.getContactNumber() != null) {
            cartItem.setContactNumber(request.getContactNumber());
        }
        
        if (request.getIdCardNumber() != null) {
            cartItem.setIdCardNumber(request.getIdCardNumber());
        }
        
        // 保存并返回
        ShoppingCart updated = shoppingCartRepository.save(cartItem);
        return convertToDTO(updated);
    }
    
    // 将实体转换为DTO
    private CartItemDTO convertToDTO(ShoppingCart cart) {
        return CartItemDTO.builder()
                .id(cart.getId())
                .userId(cart.getUserId())
                .productId(cart.getProductId())
                .productName(cart.getProductName())
                .productType(cart.getProductType())
                .contactPerson(cart.getContactPerson())
                .contactNumber(cart.getContactNumber())
                .idCardNumber(cart.getIdCardNumber())
                .paymentAmount(cart.getPaymentAmount())
                .paymentStatus(cart.getPaymentStatus() != null ? cart.getPaymentStatus().name() : null)
                .quantity(1) // 固定为1，因为订单表中没有quantity字段
                .coverImage(getProductCoverImage(cart.getProductId().longValue())) // 从商品表获取图片
                .selected(true) // 默认选中
                .build();
    }
    
    // 辅助方法：根据商品ID获取封面图片
    private String getProductCoverImage(Long productId) {
        return productRepository.findById(productId)
                .map(ProductInformation::getCoverImage)
                .orElse(null);
    }
} 