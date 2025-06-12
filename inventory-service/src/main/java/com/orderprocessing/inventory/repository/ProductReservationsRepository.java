package com.orderprocessing.inventory.repository;

import com.orderprocessing.inventory.entity.ProductReservationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReservationsRepository extends JpaRepository<ProductReservationsEntity, String> {

    public ProductReservationsEntity findByOrderId(String orderId);
}
