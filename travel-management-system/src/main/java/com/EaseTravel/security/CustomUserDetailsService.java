package com.EaseTravel.security;

import org.springframework.security.core.userdetails.UserDetailsService;

public class CustomUserDetailsService implements UserDetailsService {
    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) {
        // Minimal stub for compilation
        return null;
    }
}

