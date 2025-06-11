package com.orderprocessing.payments.repository;

import com.orderprocessing.payments.entity.PaymentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentsRepository extends JpaRepository<PaymentsEntity, String> {
}
