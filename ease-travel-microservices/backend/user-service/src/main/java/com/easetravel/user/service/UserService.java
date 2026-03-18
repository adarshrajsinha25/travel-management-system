package com.easetravel.user.service;

import com.easetravel.user.dto.request.LoginRequest;
import com.easetravel.user.dto.request.RegisterRequest;
import com.easetravel.user.dto.request.UpdateUserRequest;
import com.easetravel.user.dto.response.AuthResponse;
import com.easetravel.user.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    UserResponse getUserById(Long id);
    List<UserResponse> getAllUsers();
    UserResponse updateUser(Long id, UpdateUserRequest request);
    void deleteUser(Long id);
}

