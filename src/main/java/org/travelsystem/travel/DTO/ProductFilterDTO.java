package org.travelsystem.travel.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 商品筛选参数
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilterDTO {
    private String category;
    private String villageId;
    private String keyword;
    private Double minPrice;
    private Double maxPrice;
    private Double minRating;
    private List<String> tags;
    private String sort; // 'priceAsc' | 'priceDesc' | 'salesDesc' | 'ratingDesc' | 'newest'
} 