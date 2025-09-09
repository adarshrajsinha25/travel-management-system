package com.EaseTravel.travel_management_system.service;

import com.EaseTravel.travel_management_system.model.entity.User;
import java.util.List;

public interface UserService {
    User createUser(User user);
    User getUserById(Long id);
    List<User> getAllUsers();
    User updateUser(Long id, User user);
    void deleteUser(Long id);
}
