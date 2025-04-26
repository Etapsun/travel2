package org.travelsystem.travel.DTO;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data // Lombok注解，自动生成getter、setter、toString、equals、hashCode方法
@Builder // Lombok注解，提供Builder模式构建对象
public class ProductResponseDTO {
    private Long id; // 产品ID
    private String coverImage; // 产品封面图片URL
    private String productDescription; // 产品描述
    private Long stock; // 产品库存数量
    private String productType; // 产品类型
    private BigDecimal price; // 产品价格
    private BigDecimal discount; // 产品折扣
    private String availablePurchaseTime; // 可购买时间
    private String status; // 产品状态
    private String productCode; // 产品编码
    private String onlineTime; // 上架时间
    private String offlineTime; // 下架时间
}