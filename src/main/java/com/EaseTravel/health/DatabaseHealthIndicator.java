package com.EaseTravel.travel_management_system.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class DatabaseHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        // In a real app, check DB connection here
        return Health.up().withDetail("database", "Available").build();
    }
}
