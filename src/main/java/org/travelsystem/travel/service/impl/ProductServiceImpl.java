package org.travelsystem.travel.service.impl;

import ch.qos.logback.core.spi.ErrorCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.travelsystem.travel.DTO.*;
import org.travelsystem.travel.entity.ProductInformation;
import org.travelsystem.travel.exception.BusinessException;
import org.travelsystem.travel.mapper.ProductMapper;
import org.travelsystem.travel.respository.ProductRepository;
import org.travelsystem.travel.service.ProductService;

import javax.lang.model.type.ErrorType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    // 注入商品信息仓库
    private final ProductRepository productRepository;
    // 注入商品信息映射器
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO request) {
        // 将请求转换为实体
        ProductInformation product = productMapper.toEntity(request);
        // 设置商品状态为正常
        product.setStatus(ProductInformation.status.正常);
        // 自动生成商品编码逻辑示例
        if (product.getProductCode() == null) {
            // 生成商品编码
            product.setProductCode(generateProductCode());
        }
        // 保存商品信息并返回响应
        return productMapper.toResponse(productRepository.save(product));
    }

    @Override
    @Transactional
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO request) {
        // 根据id查找商品信息
        ProductInformation existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        // 使用BeanUtils复制属性（需添加依赖）
        BeanUtils.copyProperties(request, existing, "id");
        // 保存商品信息并返回响应
        return productMapper.toResponse(productRepository.save(existing));
    }

    @Override
    public void deleteProduct(Long id) {
        // 检查商品是否存在
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        // 执行删除操作
        productRepository.deleteById(id);
    }

    @Override
    public ProductResponseDTO getProductById(Long id) {
        // 根据id查找商品信息
        return productRepository.findById(id)
                .map(productMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
// 重写父类或接口中的方法，表示这是一个重写的方法
    public List<ProductResponseDTO> getAllProducts() {
    // 定义一个公共方法，返回所有产品的响应数据传输对象列表
        return productRepository.findAll().stream()
            // 调用productRepository的findAll方法获取所有产品实体，并将其转换为流
                .map(productMapper::toResponse)
            // 使用map操作，将每个产品实体映射为产品响应数据传输对象
                .toList();
    // 将流中的元素收集到一个List中并返回
    }


    @Override
    public List<ProductResponseDTO> getProductsByType(String typeStr) {
        // 添加字符串到枚举的转换逻辑
        // 将传入的字符串类型转换为ProductInformation.product_type枚举类型
        ProductInformation.product_type type = parseProductType(typeStr);
        // 使用productRepository根据产品类型查找产品信息
        // 调用findByProductType方法，传入转换后的枚举类型，获取匹配的产品列表
        // 将获取到的产品列表转换为流（Stream）
        // 使用map方法将每个Product对象转换为ProductResponseDTO对象
        // 最后将转换后的流收集为List并返回
        return productRepository.findByProductType(type).stream()
                .map(productMapper::toResponse)
                .toList();
    }
    // 将字符串转换为商品类型枚举
    private ProductInformation.product_type parseProductType(String typeStr) {
        // 尝试将输入的字符串转换为ProductInformation.product_type枚举类型
        try {
            // 将输入的字符串转换为大写，然后使用valueOf方法将其转换为对应的枚举值
            return ProductInformation.product_type.valueOf(typeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            // 如果转换失败，抛出运行时异常，提示无效的商品类型
            throw new RuntimeException("无效的商品类型: " + typeStr);
        }
    }

    @Override
// 重写父类或接口中的方法
    public List<ProductResponseDTO> getAvailableProducts() {
    // 定义一个方法，返回类型为List<ProductResponseDTO>，方法名为getAvailableProducts
        return productRepository.findByStatus(ProductInformation.status.正常).stream()
            // 调用productRepository的findByStatus方法，传入ProductInformation.status.正常作为参数，该方法返回一个包含所有状态为正常的产品的列表
            // 然后将该列表转换为Stream流
                .map(productMapper::toResponse)
            // 使用map方法，将每个Product对象通过productMapper的toResponse方法转换为ProductResponseDTO对象
                .toList();
            // 将转换后的Stream流重新收集为List<ProductResponseDTO>并返回
    }


    // 生成商品编码
    private String generateProductCode() {
        // 使用当前系统时间的毫秒值作为商品编码的一部分，确保每次生成的编码都是唯一的
        return "PROD-" + System.currentTimeMillis();
    }

    // 新API方法实现

    @Override
    public Map<String, Object> getProducts(ProductFilterDTO filter) {
        // 从数据库获取所有商品
        List<ProductInformation> products = productRepository.findAll();
        
        // 应用过滤条件（简化版，实际应使用更高效的查询方式）
        if (filter != null) {
            // 关键字过滤
            if (filter.getKeyword() != null && !filter.getKeyword().isEmpty()) {
                products = products.stream()
                        .filter(p -> p.getProductDescription().contains(filter.getKeyword()))
                        .collect(Collectors.toList());
            }
            
            // 价格区间过滤
            if (filter.getMinPrice() != null) {
                products = products.stream()
                        .filter(p -> p.getPrice() != null && p.getPrice().doubleValue() >= filter.getMinPrice())
                        .collect(Collectors.toList());
            }
            
            if (filter.getMaxPrice() != null) {
                products = products.stream()
                        .filter(p -> p.getPrice() != null && p.getPrice().doubleValue() <= filter.getMaxPrice())
                        .collect(Collectors.toList());
            }
            
            // 排序
            if (filter.getSort() != null) {
                switch (filter.getSort()) {
                    case "priceAsc":
                        products.sort(Comparator.comparing(ProductInformation::getPrice, 
                            Comparator.nullsLast(Comparator.naturalOrder())));
                        break;
                    case "priceDesc":
                        products.sort(Comparator.comparing(ProductInformation::getPrice, 
                            Comparator.nullsLast(Comparator.naturalOrder())).reversed());
                        break;
                    case "newest":
                        products.sort(Comparator.comparing(ProductInformation::getOnlineTime, 
                            Comparator.nullsLast(Comparator.naturalOrder())).reversed());
                        break;
                    // 其他排序方式需要添加相应的字段
                }
            }
        }
        
        // 转换为ProductDetailDTO
        List<ProductDetailDTO> productDetails = new ArrayList<>();
        
        for (ProductInformation product : products) {
            ProductDetailDTO detailDTO = ProductDetailDTO.fromProductInformation(product);
            
            // 添加规格信息
            List<ProductSpecDTO> specs = new ArrayList<>();
            
            // 确保价格不为空
            BigDecimal productPrice = product.getPrice();
            Double price = (productPrice != null) ? productPrice.doubleValue() : 0.0;
            
            // 计算原价（如果有折扣）
            Double originalPrice = null;
            if (product.getDiscount() != null && product.getDiscount().compareTo(BigDecimal.ZERO) > 0 && productPrice != null) {
                originalPrice = productPrice.divide(product.getDiscount(), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
            
            specs.add(ProductSpecDTO.builder()
                    .id("spec-" + product.getId())
                    .name("默认规格")
                    .price(price)
                    .originalPrice(originalPrice)
                    .stock(product.getStock() != null ? product.getStock().intValue() : 0)
                    .build());
            
            detailDTO.setSpecs(specs);
            
            productDetails.add(detailDTO);
        }
        
        // 返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("products", productDetails);
        result.put("total", productDetails.size());
        
        return result;
    }

    @Override
    public ProductDetailDTO getProductDetails(String id) {
        Long productId = Long.parseLong(id);
        ProductInformation product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        
        // 转换为DetailDTO并补充数据
        ProductDetailDTO detailDTO = ProductDetailDTO.fromProductInformation(product);
        
        // 添加规格信息（实际可能需要从规格表中查询）
        List<ProductSpecDTO> specs = new ArrayList<>();
        
        // 从product中获取价格，确保不为空
        BigDecimal productPrice = product.getPrice();
        Double price = (productPrice != null) ? productPrice.doubleValue() : 0.0;
        
        // 从product中获取原价（可选）
        Double originalPrice = null;
        if (product.getDiscount() != null && product.getDiscount().compareTo(BigDecimal.ZERO) > 0 && productPrice != null) {
            originalPrice = productPrice.divide(product.getDiscount(), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        
        specs.add(ProductSpecDTO.builder()
                .id("spec-" + product.getId())
                .name("默认规格")
                .price(price)
                .originalPrice(originalPrice)
                .stock(product.getStock() != null ? product.getStock().intValue() : 0)
                .build());
        
        detailDTO.setSpecs(specs);
        
        // 添加时间信息
        detailDTO.setCreatedAt(formatDateTime(product.getOnlineTime()));
        detailDTO.setUpdatedAt(formatDateTime(LocalDateTime.now()));
        
        // 添加图片列表（实际可能需要从图片表中查询）
        detailDTO.setImages(Collections.singletonList(product.getCoverImage()));
        
        return detailDTO;
    }

    @Override
    public Map<String, Object> getProductReviews(String productId, Integer page, Integer size) {
        // 模拟从数据库获取评价（实际项目中应有评价表）
        List<ProductReviewDTO> reviews = new ArrayList<>();
        
        // 创建分页参数
        int pageNum = page != null ? page : 0;
        int pageSize = size != null ? size : 10;
        
        // 返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("reviews", reviews);
        result.put("total", 0);
        
        return result;
    }

    @Override
    public ProductReviewDTO addProductReview(String productId, ProductReviewDTO review) {
        // 检查商品是否存在
        Long pId = Long.parseLong(productId);
        productRepository.findById(pId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        
        // 设置评价ID和创建时间
        review.setId(UUID.randomUUID().toString());
        review.setCreatedAt(formatDateTime(LocalDateTime.now()));
        
        // 实际项目中应将评价保存到数据库
        
        return review;
    }

    @Override
    public List<ProductDetailDTO> getRecommendedProducts() {
        // 获取一些可用商品作为推荐商品
        List<ProductInformation> products = productRepository.findByStatus(ProductInformation.status.正常)
                .stream()
                .limit(6)  // 限制返回6个
                .collect(Collectors.toList());
        
        return products.stream()
                .map(ProductDetailDTO::fromProductInformation)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDetailDTO> getRelatedProducts(String productId) {
        // 简单实现：获取一些商品作为相关商品
        Long pId = Long.parseLong(productId);
        
        // 获取除了当前商品以外的其他商品
        List<ProductInformation> products = productRepository.findByStatus(ProductInformation.status.正常)
                .stream()
                .filter(p -> !p.getId().equals(pId))
                .limit(4)  // 限制返回4个
                .collect(Collectors.toList());
        
        return products.stream()
                .map(ProductDetailDTO::fromProductInformation)
                .collect(Collectors.toList());
    }
    
    // 辅助方法：格式化日期时间
    private String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}