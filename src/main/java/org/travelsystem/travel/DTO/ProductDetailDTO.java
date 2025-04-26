package org.travelsystem.travel.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.travelsystem.travel.entity.ProductInformation;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品详细信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailDTO {
    private String id;
    private String name;
    private String category;
    private String villageId;
    private String villageName;
    private String description;
    private String details;         // 详细描述，可包含HTML
    private String coverImage;
    private List<String> images;
    private List<ProductSpecDTO> specs;
    private Boolean featured;       // 是否为精选商品
    private Boolean isNew;          // 是否为新品
    private List<String> tags;
    private Integer sales;          // 销量
    private Double rating;          // 平均评分
    private Integer reviewCount;    // 评价数量
    private List<ProductReviewDTO> reviews;
    private List<String> relatedProducts; // 相关商品ID
    private String createdAt;
    private String updatedAt;
    private Double price;           // 商品价格
    private Double originalPrice;   // 原价
    
    // 添加从ProductInformation转换的方法
    public static ProductDetailDTO fromProductInformation(ProductInformation productInfo) {
        // 计算价格
        BigDecimal price = productInfo.getPrice();
        BigDecimal originalPrice = null;
        
        // 如果有折扣，计算原价
        if (productInfo.getDiscount() != null && productInfo.getDiscount().compareTo(BigDecimal.ZERO) > 0) {
            originalPrice = price.divide(productInfo.getDiscount(), 2, BigDecimal.ROUND_HALF_UP);
        }
        
        return ProductDetailDTO.builder()
                .id(String.valueOf(productInfo.getId()))
                .name(productInfo.getProductDescription())
                .category(ProductCategoryConstants.OTHER) // 默认分类
                .description(productInfo.getProductDescription())
                .coverImage(productInfo.getCoverImage())
                .featured(false)
                .isNew(true)
                .sales(0)
                .rating(5.0)
                .reviewCount(0)
                .price(price != null ? price.doubleValue() : 0.0) // 设置价格
                .originalPrice(originalPrice != null ? originalPrice.doubleValue() : null) // 设置原价
                .build();
    }
} 