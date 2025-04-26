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
 * 服务商表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "service_provider")
public class ServiceProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty("id")
    private Integer id;

    @Column(name = "merchant_name")
    @JsonProperty("merchantName")
    private String merchantName;//商户名称

    @Column(name = "operator_name")
    @JsonProperty("operatorName")
    private String operatorName;//经营人姓名

    @Column(name = "contact_number")
    @JsonProperty("contactNumber")
    private String contactNumber;//联系电话

    @Column(name = "address")
    @JsonProperty("address")
    private String address;//服务商地址

    private enum product_type{}//产品类型***
    @Column(name = "product_type")
    @JsonProperty("productType")
    private product_type productType;

    @JsonProperty("status")
    @Column(name = "status")
    private String status;//服务商状态

    @Column(name = "business_license")
    @JsonProperty("businessLicense")
    private String business_license;//营业执照信息

    @Column(name = "business_license_pictures")
    @JsonProperty("businessLicensePictures")
    private String businessLicensePictures;//营业执照信息照片

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:SS")
    @Column(name = "creation_time")
    private LocalDateTime creationTime;//服务商创建时间

    @Column(name = "business_hours")
    @JsonProperty("businessHours")
    private String businessHours;//服务商营业时间


}
