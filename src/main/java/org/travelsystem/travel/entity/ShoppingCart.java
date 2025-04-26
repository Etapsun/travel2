package org.travelsystem.travel.entity;

import jakarta.persistence.*;
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
@Entity
@Table(name = "purchase_order")
public class ShoppingCart {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id")
    private Integer userId;
    
    @Column(name = "product_id")
    private Integer productId;
    
    @Column(name = "product_type")
    private String productType;
    
    @Column(name = "product_name")
    private String productName;
    
    @Column(name = "contact_person")
    private String contactPerson;
    
    @Column(name = "contact_number")
    private String contactNumber;
    
    @Column(name = "id_card_number")
    private String idCardNumber;
    
    @Column(name = "payment_amount")
    private BigDecimal paymentAmount;
    
    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    
    @Column(name = "payment_time")
    private LocalDateTime paymentTime;
    
    @Column(name = "creation_time")
    private LocalDateTime creationTime;
    
    @Column(name = "coupon_bool")
    private Boolean couponBool;
    
    @Column(name = "coupon_used")
    private Integer couponUsed;
    
    @Column(name = "discount_applied")
    private String discountApplied;
    
    // 注意：purchase_order表中可能没有这些字段，暂时先注释掉
    // @Column(name = "quantity")
    // private Integer quantity;
    
    // @Column(name = "cover_image")
    // private String coverImage;
} 