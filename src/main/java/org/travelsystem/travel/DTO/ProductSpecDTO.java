package org.travelsystem.travel.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 商品规格
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSpecDTO {
    private String id;
    private String name;
    private Double price;
    private Double originalPrice;  // 原价（如果有折扣）
    private Integer stock;         // 库存
    private Double weight;         // 重量(kg)
    private Map<String, String> attributes; // 规格属性，如颜色、尺寸等
} 