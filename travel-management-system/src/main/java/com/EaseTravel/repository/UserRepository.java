package com.EaseTravel.travel_management_system.repository;

import com.EaseTravel.travel_management_system.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query methods if needed
}
