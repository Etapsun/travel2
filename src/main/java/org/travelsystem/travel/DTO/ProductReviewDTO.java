package org.travelsystem.travel.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 商品评价
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductReviewDTO {
    private String id;
    private String userId;
    private String userName;
    private String userAvatar;
    private String content;
    private Integer rating;
    private List<String> images;
    private String createdAt;
    private String specs;          // 购买的规格
    private ReplyDTO reply;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReplyDTO {
        private String content;
        private String createdAt;
    }
} 