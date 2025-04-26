package org.travelsystem.travel.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Primary;
import org.travelsystem.travel.DTO.PurchaseOrderDTO;
import org.travelsystem.travel.entity.PurchaseOrder;

@Mapper(componentModel = "spring")
@Primary
public interface PurchaseOrderMapper {


    PurchaseOrderMapper INSTANCE = Mappers.getMapper(PurchaseOrderMapper.class);
    @Mapping(source = "productType", target = "productType", qualifiedByName = "stringToProductType")
    @Mapping(source = "paymentStatus", target = "paymentStatus", qualifiedByName = "stringToPaymentStatus")
    PurchaseOrder toEntity(PurchaseOrderDTO dto);

    @Mapping(source = "productType", target = "productType", qualifiedByName = "productTypeToString")
    @Mapping(source = "paymentStatus", target = "paymentStatus", qualifiedByName = "paymentStatusToString")
    PurchaseOrderDTO toDTO(PurchaseOrder entity);

    @Named("stringToProductType")
    static PurchaseOrder.product_type stringToProductType(String value) {
        return PurchaseOrder.product_type.valueOf(value);
    }

    @Named("productTypeToString")
    static String productTypeToString(PurchaseOrder.product_type type) {
        return type.name();
    }

    @Named("stringToPaymentStatus")
    static PurchaseOrder.payment_status stringToPaymentStatus(String value) {
        return PurchaseOrder.payment_status.valueOf(value);
    }

    @Named("paymentStatusToString")
    static String paymentStatusToString(PurchaseOrder.payment_status status) {
        return status.name();
    }

}