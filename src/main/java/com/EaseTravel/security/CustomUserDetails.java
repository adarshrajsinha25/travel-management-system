package com.EaseTravel.travel_management_system.security;

public class CustomUserDetails implements org.springframework.security.core.userdetails.UserDetails {
    // Custom user details implementation
    @Override
    public java.util.Collection<? extends org.springframework.security.core.GrantedAuthority> getAuthorities() { return null; }
    @Override
    public String getPassword() { return null; }
    @Override
    public String getUsername() { return null; }
    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
}

