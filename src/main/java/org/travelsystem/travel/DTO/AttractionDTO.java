package org.travelsystem.travel.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttractionDTO {
    private Long attractionId;
    private String attractionName;
    private BigDecimal attractionRating;
    private String attractionIntroduction;
    private String attractionLocation;
    private String attractionImages;
    private String attractionCover;
    private String officialGuide;
    private Integer attractionStatus;
    private String businessHours;
    private BigDecimal ticketPrice;
    private String attractionPhone;
    private Integer pageViews=0;
    private BigDecimal discount;
    private LocalDate bookingStartTime;
    private LocalDate bookingEndTime;
    private String CoverImage;
    private String DetailImage;

}