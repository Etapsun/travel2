package org.travelsystem.travel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import org.travelsystem.travel.DTO.ProductRequestDTO;
import org.travelsystem.travel.DTO.ProductResponseDTO;
import org.travelsystem.travel.entity.ProductInformation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponseDTO toResponse(ProductInformation product);


    @Mapping(target = "status", ignore = true)
    ProductInformation toEntity(ProductRequestDTO request);

    // 自定义类型转换方法
    default String map(ProductInformation.product_type type) {
        return type != null ? type.name() : null;
    }

    default String map(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;
    }
}