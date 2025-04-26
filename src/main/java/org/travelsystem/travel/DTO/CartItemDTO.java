package org.travelsystem.travel.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 购物车商品项
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private Long id;                // 购物车项ID
    private Integer userId;         // 用户ID
    private Integer productId;      // 商品ID
    private String productName;     // 商品名称
    private String productType;     // 商品类型
    private String contactPerson;   // 联系人
    private String contactNumber;   // 联系电话
    private String idCardNumber;    // 身份证号
    private BigDecimal paymentAmount; // 支付金额
    private String paymentStatus;   // 支付状态
    private Integer quantity;       // 数量（前端显示用，实际无存储）
    private String coverImage;      // 商品图片（前端显示用，实际无存储）
    private Boolean selected;       // 是否选中（前端用，实际无存储）
} 