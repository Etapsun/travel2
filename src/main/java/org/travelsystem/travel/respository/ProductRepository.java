package org.travelsystem.travel.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.travelsystem.travel.entity.ProductInformation;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductInformation, Long> {

    List<ProductInformation> findByProductType(ProductInformation.product_type type);

    List<ProductInformation> findByStatus(ProductInformation.status status);

    @Query("SELECT p FROM ProductInformation p WHERE p.onlineTime <= :now AND p.offlineTime >= :now")
    List<ProductInformation> findAvailableProducts(LocalDateTime now);
}