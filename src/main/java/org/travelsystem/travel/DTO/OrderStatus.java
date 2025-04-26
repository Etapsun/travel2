package org.travelsystem.travel.DTO;

/**
 * 订单状态
 */
public enum OrderStatus {
    PENDING_PAYMENT("pending_payment"),    // 待付款
    PAID("paid"),                         // 已付款
    SHIPPED("shipped"),                   // 已发货
    DELIVERED("delivered"),               // 已送达
    COMPLETED("completed"),               // 已完成
    CANCELLED("cancelled"),               // 已取消
    REFUNDING("refunding"),               // 退款中
    REFUNDED("refunded");                 // 已退款
    
    private final String value;
    
    OrderStatus(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
} 