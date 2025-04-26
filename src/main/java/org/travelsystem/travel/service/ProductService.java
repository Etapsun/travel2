package org.travelsystem.travel.service;

import org.travelsystem.travel.DTO.*;

import java.util.List;
import java.util.Map;

public interface ProductService {
    ProductResponseDTO createProduct(ProductRequestDTO request);
    ProductResponseDTO updateProduct(Long id, ProductRequestDTO request);
    void deleteProduct(Long id);
    ProductResponseDTO getProductById(Long id);
    List<ProductResponseDTO> getAllProducts();
    List<ProductResponseDTO> getProductsByType(String type);
    List<ProductResponseDTO> getAvailableProducts();
    Map<String, Object> getProducts(ProductFilterDTO filter);
    ProductDetailDTO getProductDetails(String id);
    Map<String, Object> getProductReviews(String productId, Integer page, Integer size);
    ProductReviewDTO addProductReview(String productId, ProductReviewDTO review);
    List<ProductDetailDTO> getRecommendedProducts();
    List<ProductDetailDTO> getRelatedProducts(String productId);
}