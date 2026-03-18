package com.easetravel.notification.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(title = "Notification Service API", version = "1.0",
                 description = "EaseTravel - Email Notification Service")
)
public class SwaggerConfig {
}

