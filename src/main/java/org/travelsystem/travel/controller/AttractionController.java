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
import java.time.LocalDate;
import java.time.LocalDateTime;
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
     * 根据ID获取景点信息 只用输入id即可获取景点信息
     * 主要面向游客
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
     * 根据名称获取景点信息，只需输入名称即可获取景点信息
     * 主要面向游客
     * 可以根据名称搜索景点信息
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
     * 给出最小的评分和最大的评分，即可获取符合评分范围的景点信息
     * 主要面向游客
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
     * 创建景点信息，需要输入所有的景点信息
     * 主要面向管理员
     * 名称
     * @param  景点信息
     * @return 景点信息 (包含ID)    景点创建成功
     * 必须的有
     */
    @PostMapping("/createAttraction")
    public ResponseEntity<AttractionDTO> createAttractionByParams(
            @RequestParam @NotNull String attractionName,
            @RequestParam @DecimalMin("0") @DecimalMax("10") BigDecimal attractionRating,
            @RequestParam(required = false) @NotNull String attractionIntroduction,
            @RequestParam(required = false) @NotNull String attractionLocation,
            @RequestParam(required = false)  String attractionImages,
            @RequestParam(required = false) String attractionCover,
            @RequestParam(required = false) String officialGuide,
            @RequestParam(required = false) Integer attractionStatus,
            @RequestParam(required = false) String businessHours,
            @RequestParam(required = false) BigDecimal ticketPrice,
            @RequestParam(required = false) String attractionPhone,
            @RequestParam(required = false) BigDecimal discount,
            @RequestParam(required = false) @PositiveOrZero Integer pageViews,
            @RequestParam @NotNull LocalDate bookingStartTime,
            @RequestParam @NotNull LocalDate bookingEndTime
            ){

        // 清理图片路径
        String cleanedImagePath = cleanImagePath(attractionImages);
        System.out.println("创建景点 - 原始图片路径: " + attractionImages);
        System.out.println("创建景点 - 清理后图片路径: " + cleanedImagePath);

        AttractionDTO dto = AttractionDTO.builder()
                .attractionName(attractionName)
                .attractionRating(attractionRating)
                .attractionIntroduction(attractionIntroduction)
                .attractionLocation(attractionLocation)
                .attractionImages(cleanedImagePath)
                .attractionCover(attractionCover)
                .officialGuide(officialGuide)
                .attractionStatus(attractionStatus)
                .businessHours(businessHours)
                .ticketPrice(ticketPrice)
                .attractionPhone(attractionPhone)
                .discount(discount)
                .bookingStartTime(bookingStartTime)
                .bookingEndTime(bookingEndTime)
                .build();

        return ResponseEntity.ok(attractionService.createAttraction(dto));
    }


    // 处理PUT请求，更新景点信息，
    @PutMapping("/updateAttraction/{id}")
    public ResponseEntity<AttractionDTO> updateAttraction(
            @PathVariable Long id, // 从URL路径中获取景点的ID
            @RequestParam String attractionName, // 获取景点名称
            @RequestParam(required = false) BigDecimal attractionRating, // 获取景点评分，非必需参数
            @RequestParam(required = false) String attractionIntroduction, // 获取景点介绍，非必需参数
            @RequestParam(required = false) String attractionLocation, // 获取景点位置，非必需参数
            @RequestParam(required = false) String attractionImages, // 获取景点图片，非必需参数
            @RequestParam(required = false) String attractionCover, // 获取景点封面图片，非必需参数
            @RequestParam(required = false) String officialGuide, // 获取官方指南，非必需参数
            @RequestParam(required = false) Integer attractionStatus, // 获取景点状态，非必需参数
            @RequestParam(required = false) String businessHours, // 获取营业时间，非必需参数
            @RequestParam(required = false) BigDecimal ticketPrice, // 获取门票价格，非必需参数
            @RequestParam(required = false) BigDecimal discount, // 获取折扣信息，非必需参数
            @RequestParam(required = false) String attractionPhone, // 获取景点电话，非必需参数
            @RequestParam(required = false) LocalDate bookingStartTime, // 获取预订开始时间，非必需参数
            @RequestParam(required = false) LocalDate bookingEndTime // 获取预订结束时间，非必需参数
    ) {
        // 清理图片路径
        String cleanedImagePath = cleanImagePath(attractionImages);
        System.out.println("原始图片路径: " + attractionImages);
        System.out.println("清理后图片路径: " + cleanedImagePath);
        
        // 构建DTO并调用service
        AttractionDTO dto = AttractionDTO.builder() // 使用Builder模式构建AttractionDTO对象
                .attractionId(id) // 设置景点ID
                .attractionName(attractionName) // 设置景点名称
                .attractionRating(attractionRating) // 设置景点评分
                .attractionIntroduction(attractionIntroduction) // 设置景点介绍
                .attractionLocation(attractionLocation) // 设置景点位置
                .attractionImages(cleanedImagePath) // 设置已清理的景点图片路径
                .attractionCover(attractionCover) // 设置景点封面图片
                .officialGuide(officialGuide) // 设置官方指南
                .attractionStatus(attractionStatus) // 设置景点状态
                .businessHours(businessHours) // 设置营业时间
                .ticketPrice(ticketPrice) // 设置门票价格
                .attractionPhone(attractionPhone) // 设置景点电话
                .discount(discount) // 设置折扣信息
                .bookingStartTime(bookingStartTime) // 设置预订开始时间
                .bookingEndTime(bookingEndTime) // 设置预订结束时间
                .build(); // 构建DTO对象
        return ResponseEntity.ok(attractionService.updateAttraction(dto)); // 调用service更新景点信息，并返回响应实体
    }

    // 辅助方法：清理图片路径，移除路径中的../
    private String cleanImagePath(String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) {
            return imagePath;
        }
        
        // 如果路径包含../../，提取文件名
        if (imagePath.contains("../")) {
            // 从路径中提取文件名
            String[] parts = imagePath.split("/");
            String fileName = parts[parts.length - 1];
            
            // 返回干净的路径
            return "/uploads/" + fileName;
        }
        
        return imagePath;
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
     * 获取所有景点信息，只传数据
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

    /**
     * 获取景点图片（提供直接访问图片的接口）
     * @param id 景点ID
     * @return 图片URL
     */
    @GetMapping("/{id}/images")
    public ResponseEntity<?> getAttractionImages(@PathVariable Long id) {
        try {
            AttractionDTO attraction = attractionService.getAttractionById(id);
            if (attraction == null || attraction.getAttractionId() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("code", 404, "message", "景点不存在"));
            }
            
            // 清理并返回图片路径
            String cleanImagePath = cleanImagePath(attraction.getAttractionImages());
            System.out.println("获取景点图片 - 原始路径: " + attraction.getAttractionImages());
            System.out.println("获取景点图片 - 清理后路径: " + cleanImagePath);
            
            // 返回图片路径数据
            return ResponseEntity.ok(Map.of(
                "imageUrl", cleanImagePath,
                "attractionId", id
            ));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("code", 404, "message", e.getMessage()));
        }
    }

}








