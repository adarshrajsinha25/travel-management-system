package com.EaseTravel.repository;

import com.EaseTravel.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // Custom query methods if needed
}
