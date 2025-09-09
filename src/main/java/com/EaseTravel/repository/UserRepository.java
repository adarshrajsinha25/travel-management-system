package com.EaseTravel.repository;

import com.EaseTravel.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query methods if needed
}
