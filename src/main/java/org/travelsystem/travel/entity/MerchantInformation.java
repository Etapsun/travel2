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
 *  商户信息表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "merchant_information")
public class MerchantInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    @Column(name = "id")
    private Long id;

    @Column(name = "merchant_name")
    @JsonProperty("merchantName")
    private String merchantName;//商户名称

    @Column(name = "cover_image")
    @JsonProperty("coverImages")
    private String cover_images;//封面图URL

    @Column(name = "store_address")
    @JsonProperty("storeAddress")
    private String storeAddress;//店铺地址

    @Column(name = "contact_number")
    @JsonProperty("contactNumber")
    private Integer contactNumber;

    @Column(name = "service_provider_id")
    @JsonProperty("serviceProviderId")
    private Integer serviceProviderId;//关联服务商表id

    @Column(name = "creation_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:SS")
    private LocalDateTime creationTime;//创建时间


    private enum merchant_type {餐饮,商品贩卖,居住服务,其他};//商户类型
    @JsonProperty("merchantType")
    @Column(name = "merchant_type")
    private merchant_type merchant_type;

    @Column(name = "business_scope")
    @JsonProperty("businessScope")
    private String businessScope;//经营范围描述

}
