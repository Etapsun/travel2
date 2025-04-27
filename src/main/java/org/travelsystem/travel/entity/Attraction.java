package org.travelsystem.travel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 *景点表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "attraction")
@Builder(toBuilder = true)
public class Attraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增主键（MySQL/Auto）
    @JsonProperty("attractionId")
    @Column(name = "attraction_Id")
    private Long attractionId; // 主键字段

    @JsonProperty("attractionName")
    @Column(name = "attraction_name")
    private String attractionName;//景点名字

    @JsonProperty("attractionRating")
    @Column(name = "attraction_rating")
    private BigDecimal attractionRating;//景点评分

    @JsonProperty("attractionIntroduction")
    @Column(name = "attraction_introduction")
    private String attractionIntroduction;

    @JsonProperty("attractionLocation")
    @Column(name = "attraction_location")
    private String attractionLocation;//景点地址

    @JsonProperty("attractionImages")
    @Column(name = "attraction_images")
    private String attractionImages;//景点图片

    @JsonProperty("attractionCover")
    @Column(name = "attraction_cover")
    private String attractionCover;//景点封面图


    private String officialGuide;//景点官方攻略

    @JsonProperty("attractionStatus")
    @Column(name = "attraction_status")
    private Integer attractionStatus;//景点状态

    @JsonProperty("businessHours")
    @Column(name = "business_hours")
    private String businessHours;//景点营业时间

    @JsonProperty("pageViews")
    @Column(name="page_views")
    private Integer pageViews;//景点页面浏览量

    @JsonProperty("ticketPrice")
    @Column(name = "ticket_price")
    private BigDecimal ticketPrice;//景点门票价格

    @JsonProperty("discount")
    @Column(name = "discount")
    private BigDecimal discount;//景点享受折扣

    @JsonProperty("attractionPhone")
    @Column(name = "attraction_phone")
    private String attractionPhone;//景点电话

    @Column(name = "booking_start_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDate bookingStartTime;

    @Column(name = "booking_end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDate bookingEndTime;

}
