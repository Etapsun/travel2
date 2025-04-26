package org.travelsystem.travel.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 订单信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private String id;
    private String userId;
    private List<OrderItemDTO> items;
    private Double totalAmount;
    private String status;
    private ShippingAddressDTO shippingAddress;
    private String paymentMethod; // 'wechat' | 'alipay' | 'creditCard'
    private String paymentTime;
    private String shippingTime;
    private String deliveryTime;
    private String completeTime;
    private String cancelTime;
    private String trackingNumber;
    private String shippingCompany;
    private String remark;
    private String createdAt;
    private String updatedAt;
    
    /**
     * 收货地址
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShippingAddressDTO {
        private String name;
        private String phone;
        private String province;
        private String city;
        private String district;
        private String address;
        private String zipCode;
    }
} 