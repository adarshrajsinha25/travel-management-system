# Travel Management System (EaseTravel)

A Spring Boot-based travel booking and management application that provides REST APIs and web views for trips, bookings, hotels, flights, payments, users and notifications.

## Features
- User authentication and authorization
- Trip, booking, hotel and flight management
- Payment processing (simulated)
- Email and notification scaffolding
- Spring Actuator health checks
- Event-driven components and schedulers
- OpenAPI/Swagger support

## Prerequisites
- Java 17+ (JDK)
- Maven 3.6+
- Docker & Docker Compose (optional, for containerized run)

## Build
From project root (uses the bundled wrapper):

Windows:

    mvnw.cmd clean package -DskipTests

Unix/macOS:

    ./mvnw clean package -DskipTests

Or run directly during development:

    ./mvnw spring-boot:run

## Run
After building:

    java -jar target/*.jar

Or using Maven run:

    ./mvnw spring-boot:run

## Docker / Compose
The repository includes a `compose.yaml` and Dockerfile for containerized runs. Example (development):

    docker compose -f compose.yaml up --build

## Configuration
- Application configuration files are under `src/main/resources/` (profiles: `application.properties`, `application-dev.yml`, etc.).
- Database migrations live in `src/main/resources/db/migration/` (Flyway-style SQL files).
- Secrets and credentials should be provided via environment variables or an external secrets store—do not commit secrets to source control.

## Project Structure (high level)
- `src/main/java/com/EaseTravel/` — main application packages
  - `config/` — Spring configurations (security, web, async, cache, etc.)
  - `controller/` — REST and web controllers (API versions under `controller/api/v1`, `controller/api/v2`)
  - `model/` — entities, DTOs and enums
  - `repository/` — Spring Data JPA repositories
  - `service/` — service interfaces and implementations
  - `mapper/` — mapping components between entities and DTOs
  - `exception/` — custom exceptions and global handlers
  - `event/` — application events and publisher
  - `scheduler/` — scheduled jobs
  - `util/` — utility classes and constants

## Tests
Run unit and integration tests with:

    ./mvnw test

Test resources live under `src/test/resources/`.

## Common development notes
- Package declarations must match the directory structure under `src/main/java/com/EaseTravel`. If you get compilation errors about mismatched package and file paths, update the `package` line to match the folder path (e.g. `package com.EaseTravel.service;`).
- The project uses Spring Boot and standard annotations; enable your IDE's annotation processing and Maven import features.

## Contributing
- Fork the repository, make changes on a feature branch and open a PR against `main`.
- Add tests for new behavior and follow the existing code style.

## License
See the `LICENSE` file at the repository root.

## Contact
For questions or issues, open an issue in this repository.

