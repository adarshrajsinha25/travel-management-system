package com.easetravel.trip.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(title = "Trip Service API", version = "1.0",
                 description = "EaseTravel - Trip, Flight, Hotel & Destination Management")
)
public class SwaggerConfig {
}

