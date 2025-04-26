package org.travelsystem.travel.controller;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.travelsystem.travel.DTO.AttractionDTO;
import org.travelsystem.travel.exception.BusinessException;
import org.travelsystem.travel.service.AttractionService;
import org.travelsystem.travel.service.impl.ResourceNotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/api/attractions")
@RequiredArgsConstructor
public class AttractionController {
    // 使用@RequiredArgsConstructor注解自动生成一个包含所有final字段的构造函数
    private final AttractionService attractionService;


    /**
     * 根据ID获取景点信息
     * @param id 景点ID
     * @return 景点信息
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getAttractionById(@PathVariable Long id) {
        try {
            AttractionDTO result = attractionService.getAttractionById(id);
            if (result == null || result.getAttractionId() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("code", 404, "message", "景点不存在"));
            }
            return ResponseEntity.ok(result);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("code", 404, "message", e.getMessage()));
        }
    }

    /**
     * 根据名称获取景点信息
     * @param name 景点名称
     * @return 景点信息
     */
    @GetMapping("/search")
    public ResponseEntity<List<AttractionDTO>> searchAttractionsByName(
            @RequestParam String name) {
        // 调用服务层方法根据名称搜索景点，并返回响应实体
        return ResponseEntity.ok(attractionService.searchAttractionsByName(name));
    }

    /**
     * 根据评分范围获取景点信息
     * @param min 最小评分
     * @param max 最大评分
     * @return 景点信息
     */
    @GetMapping("/rating")
    public ResponseEntity<?> getAttractionsByRatingRange(
            @RequestParam @NotNull(message = "min不能为空") @DecimalMin(value = "0.0", message = "评分最小值不能小于0") BigDecimal min,
            @RequestParam @NotNull(message = "max不能为空") @DecimalMax(value = "10.0", message = "评分最大值不能超过10") BigDecimal max){
        try {
            List<AttractionDTO> result = attractionService.getAttractionsByRatingRange(min, max);
            if (result.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of(
                                "code", 404,
                                "message", "没有找到符合评分范围的景点",
                                "data", null
                        ));
            }
            return ResponseEntity.ok(result);
        } catch (BusinessException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "code", 400,
                            "message", e.getMessage(),
                            "data", null
                    ));
        }
    }

    /**
     * 创建景点信息
     */
    @PostMapping("/createAttraction")
    public ResponseEntity<AttractionDTO> createAttractionByParams(
            @RequestParam String attractionName,
            @RequestParam @DecimalMin("0") @DecimalMax("10") BigDecimal attractionRating,
            @RequestParam(required = false) String attractionIntroduction,
            @RequestParam(required = false) String attractionLocation,
            @RequestParam(required = false) String attractionImages,
            @RequestParam(required = false) String attractionCover,
            @RequestParam(required = false) String officialGuide,
            @RequestParam(required = false) Integer attractionStatus,
            @RequestParam(required = false) String businessHours,
            @RequestParam(required = false) BigDecimal ticketPrice,
            @RequestParam(required = false) String attractionPhone,
            @RequestParam(required = false) BigDecimal discount,
            @RequestParam(required = false) @PositiveOrZero Integer pageViews
            ){


        AttractionDTO dto = AttractionDTO.builder()
                .attractionName(attractionName)
                .attractionRating(attractionRating)
                .attractionIntroduction(attractionIntroduction)
                .attractionLocation(attractionLocation)
                .attractionImages(attractionImages)
                .attractionCover(attractionCover)
                .officialGuide(officialGuide)
                .attractionStatus(attractionStatus)
                .businessHours(businessHours)
                .ticketPrice(ticketPrice)
                .attractionPhone(attractionPhone)
                .discount(discount)
                .build();

        return ResponseEntity.ok(attractionService.createAttraction(dto));
    }


    // 处理PUT请求，更新景点信息
    @PutMapping("/updateAttraction/{id}")
    public ResponseEntity<AttractionDTO> updateAttraction(
            @PathVariable Long id,
            @RequestParam String attractionName,
            @RequestParam(required = false) BigDecimal attractionRating,
            @RequestParam(required = false) String attractionIntroduction,
            @RequestParam(required = false) String attractionLocation,
            @RequestParam(required = false) String attractionImages,
            @RequestParam(required = false) String attractionCover,
            @RequestParam(required = false) String officialGuide,
            @RequestParam(required = false) Integer attractionStatus,
            @RequestParam(required = false) String businessHours,
            @RequestParam(required = false) BigDecimal ticketPrice,
            @RequestParam(required = false) BigDecimal discount,
            @RequestParam(required = false) String attractionPhone
    ) {
        // 构建DTO并调用service
        AttractionDTO dto = AttractionDTO.builder()
                .attractionId(id)
                .attractionName(attractionName)
                .attractionRating(attractionRating)
                .attractionIntroduction(attractionIntroduction)
                .attractionLocation(attractionLocation)
                .attractionImages(attractionImages)
                .attractionCover(attractionCover)
                .officialGuide(officialGuide)
                .attractionStatus(attractionStatus)
                .businessHours(businessHours)
                .ticketPrice(ticketPrice)
                .attractionPhone(attractionPhone)
                .discount(discount)
                .build();
        return ResponseEntity.ok(attractionService.updateAttraction(dto));
    }


    // 处理DELETE请求，根据ID删除景点
        @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAttraction(@PathVariable Long id) {
        // 调用服务层方法删除景点
        attractionService.deleteAttraction(id);
        // 返回204 No Content响应
        return ResponseEntity.ok().build();
    }

    // 处理PUT请求，增加景点的页面浏览次数
    @PutMapping("/{id}/views")
    public ResponseEntity<Void> incrementPageViews(@PathVariable Long id) {
        // 调用服务层方法增加景点的页面浏览次数
        attractionService.incrementPageViews(id);
        // 返回200 OK响应
        return ResponseEntity.ok().build();
    }

    /**
     * 获取所有景点信息
     * @return
     */
    @GetMapping("/getAllAttractions")
    public ResponseEntity<List<AttractionDTO>> getAllAttractions() {
        return ResponseEntity.ok(attractionService.getAllAttractions());
    }

    /**
     * 获取所有景点信息，包含详细信息，与上面不同的是这个是全部的信息，运用于详细页面
     * @return
     */
    @GetMapping("/getAllAttractionDetailed")
    public ResponseEntity<List<AttractionDTO>> getAllAttractionDetailed() {
        return ResponseEntity.ok(attractionService.getAllAttractionDetailed());
    }




}








