package org.travelsystem.travel.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 购买订单表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "purchase_order")
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty("id")
    private Long id;

    @Column(name = "user_id")
    @JsonProperty("userId")
    private Integer userId;//关联表用户的id

    @Column(name = "product_id")
    @JsonProperty("productId")
    private Integer productId;//关联商品表的id


    public enum product_type{门票,居住服务,商品贩卖,餐饮服务,其他}//商品类型
    @Enumerated(EnumType.STRING)
    @Column(name = "product_type")
    @JsonProperty("productType")
    private product_type productType;

    @JsonProperty("productName")
    @Column(name = "product_name")
    private String productName;//商品名称

    @Column(name = "contact_person")
    @JsonProperty("contactPerson")
    private String contactPerson;//联系人姓名

    @Column(name = "contact_number")
    @JsonProperty("contactNumber")
    private String contactNumber;//联系电话

    @Column(name = "id_card_number")
    @JsonProperty("idCardNumber")
    private String idCardNumber;//身份证号码

    @Column(name = "payment_amount")
    @JsonProperty("payment_amount")
    private BigDecimal payment_amount;//实际支付金额


    public enum payment_status{支付中,支付成功,支付失败,未付款};//支付状态
    @JsonProperty("paymentStatus")
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private payment_status paymentStatus;

    @Column(name = "payment_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:SS")
    private LocalDateTime paymentTime;//支付时间

    @Column(name = "creation_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:SS")
    private LocalDateTime creationTime;//订单创建时间

    @Column(name = "coupon_bool")
    @JsonProperty("couponBool")
    private boolean couponBool;

    @Column(name = "coupon_used")
    @JsonProperty("couponUsed")
    private Integer couponUsed;//使用的优惠券编号

    @Column(name = "discount_applied")
    @JsonProperty("discountApplied")
    private String discount_applied;//最终应用折扣
}