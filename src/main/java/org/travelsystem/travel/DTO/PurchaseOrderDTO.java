package org.travelsystem.travel.DTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PurchaseOrderDTO {
    private Long id;
    private Integer userId;
    private Integer productId;
    private String productType; // 枚举转换为字符串
    private String productName;
    private String contactPerson;
    private String contactNumber;
    private String idCardNumber;
    private BigDecimal paymentAmount;
    private String paymentStatus; // 枚举转换为字符串
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paymentTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationTime;
    private boolean couponBool;
    private Integer couponUsed;
    private String discountApplied;
}