package org.travelsystem.travel.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import org.travelsystem.travel.DTO.ProductRequestDTO;
import org.travelsystem.travel.DTO.ProductResponseDTO;
import org.travelsystem.travel.entity.ProductInformation;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-28T01:44:53+0800",
    comments = "version: 1.6.0, compiler: javac, environment: Java 17.0.14 (Microsoft)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductResponseDTO toResponse(ProductInformation product) {
        if ( product == null ) {
            return null;
        }

        ProductResponseDTO.ProductResponseDTOBuilder productResponseDTO = ProductResponseDTO.builder();

        productResponseDTO.id( product.getId() );
        productResponseDTO.coverImage( product.getCoverImage() );
        productResponseDTO.productDescription( product.getProductDescription() );
        productResponseDTO.stock( product.getStock() );
        productResponseDTO.productType( map( product.getProductType() ) );
        productResponseDTO.price( product.getPrice() );
        productResponseDTO.discount( product.getDiscount() );
        productResponseDTO.availablePurchaseTime( map( product.getAvailablePurchaseTime() ) );
        if ( product.getStatus() != null ) {
            productResponseDTO.status( product.getStatus().name() );
        }
        productResponseDTO.productCode( product.getProductCode() );
        productResponseDTO.onlineTime( map( product.getOnlineTime() ) );
        productResponseDTO.offlineTime( map( product.getOfflineTime() ) );

        return productResponseDTO.build();
    }

    @Override
    public ProductInformation toEntity(ProductRequestDTO request) {
        if ( request == null ) {
            return null;
        }

        ProductInformation.ProductInformationBuilder productInformation = ProductInformation.builder();

        productInformation.coverImage( request.getCoverImage() );
        productInformation.productDescription( request.getProductDescription() );
        productInformation.stock( request.getStock() );
        productInformation.productType( request.getProductType() );
        productInformation.price( request.getPrice() );
        productInformation.discount( request.getDiscount() );
        productInformation.availablePurchaseTime( request.getAvailablePurchaseTime() );
        productInformation.productCode( request.getProductCode() );
        productInformation.onlineTime( request.getOnlineTime() );
        productInformation.offlineTime( request.getOfflineTime() );

        return productInformation.build();
    }
}
