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
 * 产品信息
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_information")
public class ProductInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty("id")
    private Long id;

    @Column(name = "cover_image")
    @JsonProperty("coverImage")
    private String coverImage;//商品封面图url**

    @Column(name = "product_description")
    @JsonProperty("productDescription")
    private String productDescription;//商品描述

    @Column(name = "stock")
    @JsonProperty("stock")
    private Long stock;//库存数量


    public enum product_type{门票,居住服务,商品贩卖,餐饮服务,其他}
    @Column(name = "product_type")
    @JsonProperty("productType")
    @Enumerated(EnumType.STRING)
    private product_type productType;

    @Column(name = "price")
    @JsonProperty("price")
    private BigDecimal price;//商品价格

    @Column(name = "discount")
    @JsonProperty("discount")
    private BigDecimal discount;//折扣

    @Column(name = "available_purchase_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:SS")
    private LocalDateTime availablePurchaseTime;//可够秒时间段**


    public enum status{上架,下架,已经贩卖,正常}//商品状态
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @JsonProperty("status")
    private status status;

    @Column(name = "product_code")
    @JsonProperty("productCode")
    private String productCode;//商品编码

    @Column(name = "online_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:SS")
    private LocalDateTime onlineTime;//上架时间

    @Column(name = "offline_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:SS")
    private LocalDateTime offlineTime;//下架时间
}
