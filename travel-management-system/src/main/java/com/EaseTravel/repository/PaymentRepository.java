package com.EaseTravel.travel_management_system.repository;

import com.EaseTravel.travel_management_system.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // Custom query methods if needed
}
