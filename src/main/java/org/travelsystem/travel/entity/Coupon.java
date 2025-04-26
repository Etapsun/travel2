package org.travelsystem.travel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 优惠券表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="coupon")
public class Coupon
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增主键（MySQL/Auto）
    @Column(name = "id")
    @JsonProperty("id")
    private Long id; // 主键字段

    @JsonProperty("couponCode")
    @Column(name = "coupon_code")
    private String couponCode;//优惠券编码


    private enum discount_type {百分比,固定价格};

    @Column(name = "discount_type")
    @JsonProperty("discountType")
    private discount_type discountType;

    @JsonProperty("discountValue")
    @Column(name = "discount_value")
    private double discount_value;//折扣值

    @Column(name = "valid_start")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:SS")
    private LocalDateTime vaild_start;//折扣开始时间

    @Column(name = "valid_end")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime vaild_end;//折扣结束时间


    private enum applicable_product_type{商品,景点,餐饮};//适用商品类型
    @JsonProperty("applicableProductType")
    @Column(name = "applicable_product_type")
    private applicable_product_type applicable_product_type;

    @Column(name = "coupon_name")
    @JsonProperty("")
    private String coupon_name;//优惠券名称


}
