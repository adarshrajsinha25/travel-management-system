package com.EaseTravel.service.impl;

import com.EaseTravel.model.entity.User;
import com.EaseTravel.repository.UserRepository;
import com.EaseTravel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long id, User user) {
        Optional<User> existing = userRepository.findById(id);
        if (existing.isPresent()) {
            User u = existing.get();
            u.setUsername(user.getUsername());
            u.setEmail(user.getEmail());
            u.setPassword(user.getPassword());
            return userRepository.save(u);
        }
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
