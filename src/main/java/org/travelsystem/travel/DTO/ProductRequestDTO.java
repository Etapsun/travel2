package org.travelsystem.travel.DTO;
import org.travelsystem.travel.entity.ProductInformation;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {
    // 商品封面图，不能为空
    @NotBlank(message = "封面图不能为空")
    private String coverImage;

    // 商品描述，不能为空
    @NotBlank(message = "商品描述不能为空")
    private String productDescription;

    // 库存，不能为负数
    @NotNull
    @Min(value = 0, message = "库存不能为负数")
    private Long stock;

    // 商品类型
    private ProductInformation.product_type productType;

    // 价格，不能为负数
    @NotNull
    @DecimalMin(value = "0.0", message = "价格不能为负数")
    private BigDecimal price;

    // 折扣，最小值为0.0，最大值为1.0
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "1.0")
    private BigDecimal discount;

    // 可购买时间，格式为yyyy-MM-dd HH:mm:ss
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime availablePurchaseTime;

    // 商品编码
    private String productCode;

    // 上线时间，格式为yyyy-MM-dd HH:mm:ss
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime onlineTime;

    // 下线时间，格式为yyyy-MM-dd HH:mm:ss
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime offlineTime;
}