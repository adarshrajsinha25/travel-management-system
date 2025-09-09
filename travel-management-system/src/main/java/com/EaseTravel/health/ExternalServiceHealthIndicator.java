package com.EaseTravel.travel_management_system.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class ExternalServiceHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        // In a real app, check external service availability here
        return Health.up().withDetail("externalService", "Available").build();
    }
}
