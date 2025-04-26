package org.travelsystem.travel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.travelsystem.travel.DTO.*;
import org.travelsystem.travel.service.ProductService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "商品管理", description = "商品相关接口")
public class ProductController {

    // 注入ProductService
    private final ProductService productService;

    // 创建产品
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "创建商品", description = "创建新商品信息")
    public ProductResponseDTO createProduct(@Valid @RequestBody ProductRequestDTO request) {
        // 调用ProductService的createProduct方法创建产品
        return productService.createProduct(request);
    }

    // 根据id获取产品
    @GetMapping("/{id}")
    @Operation(summary = "获取商品详情", description = "根据ID获取商品详细信息")
    public ProductDetailDTO getProduct(
            @Parameter(description = "商品ID") @PathVariable String id) {
        // 调用新方法获取商品详情
        return productService.getProductDetails(id);
    }

    // 获取所有产品 - 修改为使用过滤条件
    @GetMapping
    @Operation(summary = "获取商品列表", description = "根据条件筛选商品")
    public Map<String, Object> getAllProducts(
            @Parameter(description = "筛选参数") ProductFilterDTO filter) {
        // 调用新的getProducts方法，根据条件获取商品
        return productService.getProducts(filter);
    }

    // 根据类型获取产品
    @GetMapping("/type/{type}")
    @Operation(summary = "按类型获取商品", description = "根据商品类型获取商品列表")
    public List<ProductResponseDTO> getByType(
            @Parameter(description = "商品类型") @PathVariable String type) {
        // 调用ProductService的getProductsByType方法根据类型获取产品
        return productService.getProductsByType(type);
    }

    // 更新产品
    @PutMapping("/{id}")
    @Operation(summary = "更新商品", description = "根据ID更新商品信息")
    public ProductResponseDTO updateProduct(
            @Parameter(description = "商品ID") @PathVariable Long id,
            @Valid @RequestBody ProductRequestDTO request
    ) {
        // 调用ProductService的updateProduct方法更新产品
        return productService.updateProduct(id, request);
    }

    // 删除产品
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "删除商品", description = "根据ID删除商品")
    public void deleteProduct(
            @Parameter(description = "商品ID") @PathVariable Long id) {
        // 调用ProductService的deleteProduct方法删除产品
        productService.deleteProduct(id);
    }

    // 获取商品评价
    @GetMapping("/{productId}/reviews")
    @Operation(summary = "获取商品评价", description = "获取指定商品的评价列表")
    public Map<String, Object> getProductReviews(
            @Parameter(description = "商品ID") @PathVariable String productId,
            @Parameter(description = "页码") @RequestParam(required = false) Integer page,
            @Parameter(description = "每页数量") @RequestParam(required = false) Integer size
    ) {
        return productService.getProductReviews(productId, page, size);
    }

    // 添加商品评价
    @PostMapping("/{productId}/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "添加商品评价", description = "为指定商品添加新评价")
    public ProductReviewDTO addProductReview(
            @Parameter(description = "商品ID") @PathVariable String productId,
            @Valid @RequestBody ProductReviewDTO review
    ) {
        return productService.addProductReview(productId, review);
    }

    // 获取推荐商品
    @GetMapping("/recommended")
    @Operation(summary = "获取推荐商品", description = "获取系统推荐的商品列表")
    public List<ProductDetailDTO> getRecommendedProducts() {
        return productService.getRecommendedProducts();
    }

    // 获取相关商品
    @GetMapping("/{productId}/related")
    @Operation(summary = "获取相关商品", description = "获取与指定商品相关的商品列表")
    public List<ProductDetailDTO> getRelatedProducts(
            @Parameter(description = "商品ID") @PathVariable String productId) {
        return productService.getRelatedProducts(productId);
    }
}