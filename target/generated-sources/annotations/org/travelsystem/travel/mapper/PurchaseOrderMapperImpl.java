package org.travelsystem.travel.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import org.travelsystem.travel.DTO.PurchaseOrderDTO;
import org.travelsystem.travel.entity.PurchaseOrder;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-29T00:57:20+0800",
    comments = "version: 1.6.0, compiler: javac, environment: Java 17.0.14 (Microsoft)"
)
@Component
public class PurchaseOrderMapperImpl implements PurchaseOrderMapper {

    @Override
    public PurchaseOrder toEntity(PurchaseOrderDTO dto) {
        if ( dto == null ) {
            return null;
        }

        PurchaseOrder.PurchaseOrderBuilder purchaseOrder = PurchaseOrder.builder();

        purchaseOrder.productType( PurchaseOrderMapper.stringToProductType( dto.getProductType() ) );
        purchaseOrder.paymentStatus( PurchaseOrderMapper.stringToPaymentStatus( dto.getPaymentStatus() ) );
        purchaseOrder.id( dto.getId() );
        purchaseOrder.userId( dto.getUserId() );
        purchaseOrder.productId( dto.getProductId() );
        purchaseOrder.productName( dto.getProductName() );
        purchaseOrder.contactPerson( dto.getContactPerson() );
        purchaseOrder.contactNumber( dto.getContactNumber() );
        purchaseOrder.idCardNumber( dto.getIdCardNumber() );
        purchaseOrder.paymentTime( dto.getPaymentTime() );
        purchaseOrder.creationTime( dto.getCreationTime() );
        purchaseOrder.couponBool( dto.isCouponBool() );
        purchaseOrder.couponUsed( dto.getCouponUsed() );

        return purchaseOrder.build();
    }

    @Override
    public PurchaseOrderDTO toDTO(PurchaseOrder entity) {
        if ( entity == null ) {
            return null;
        }

        PurchaseOrderDTO purchaseOrderDTO = new PurchaseOrderDTO();

        purchaseOrderDTO.setProductType( PurchaseOrderMapper.productTypeToString( entity.getProductType() ) );
        purchaseOrderDTO.setPaymentStatus( PurchaseOrderMapper.paymentStatusToString( entity.getPaymentStatus() ) );
        purchaseOrderDTO.setId( entity.getId() );
        purchaseOrderDTO.setUserId( entity.getUserId() );
        purchaseOrderDTO.setProductId( entity.getProductId() );
        purchaseOrderDTO.setProductName( entity.getProductName() );
        purchaseOrderDTO.setContactPerson( entity.getContactPerson() );
        purchaseOrderDTO.setContactNumber( entity.getContactNumber() );
        purchaseOrderDTO.setIdCardNumber( entity.getIdCardNumber() );
        purchaseOrderDTO.setPaymentTime( entity.getPaymentTime() );
        purchaseOrderDTO.setCreationTime( entity.getCreationTime() );
        purchaseOrderDTO.setCouponBool( entity.isCouponBool() );
        purchaseOrderDTO.setCouponUsed( entity.getCouponUsed() );

        return purchaseOrderDTO;
    }
}
