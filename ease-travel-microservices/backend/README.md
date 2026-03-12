# ✈️ EaseTravel — Microservices Architecture

A fully refactored **Spring Boot 3.3.4 + Spring Cloud 2023.0.3** microservices system for travel management.

---

## 📐 Architecture Overview

```
                        ┌──────────────────────┐
                        │   API Gateway :8080   │
                        │ (JWT Validation +     │
                        │  Load Balancing)      │
                        └──────────┬───────────┘
                                   │
              ┌────────────────────┼────────────────────┐
              │                    │                    │
    ┌─────────▼──────┐   ┌─────────▼──────┐   ┌────────▼───────┐
    │  User Service  │   │  Trip Service  │   │Booking Service │
    │    :8081       │   │    :8082       │   │    :8083       │
    │  JWT Auth      │   │  Trips/Flights │   │  Feign→Trip    │
    └────────────────┘   │  Hotels/Dest.  │   └────────┬───────┘
                         └────────────────┘            │
              ┌────────────────────────────────────────┤
              │                                        │
    ┌─────────▼──────┐                      ┌──────────▼─────┐
    │Payment Service │                      │  Notification  │
    │    :8084       │                      │  Service :8085 │
    │ Feign→Booking  │                      │  Email (SMTP)  │
    └────────────────┘                      └────────────────┘
              │
    ┌─────────▼──────────────────┐
    │  Eureka Discovery  :8761   │
    └────────────────────────────┘
```

---

## 🧩 Services

| Service               | Port | Description                                    |
|-----------------------|------|------------------------------------------------|
| `discovery-server`    | 8761 | Eureka Service Registry                        |
| `api-gateway`         | 8080 | Spring Cloud Gateway + JWT filter              |
| `user-service`        | 8081 | User registration, login, JWT token generation |
| `trip-service`        | 8082 | Trips, Flights, Hotels, Destinations (CRUD)    |
| `booking-service`     | 8083 | Create/cancel bookings (calls trip-service)    |
| `payment-service`     | 8084 | Payment processing (calls booking-service)     |
| `notification-service`| 8085 | Email notifications via SMTP / MailHog         |

---

## 🛠️ Tech Stack

- **Spring Boot 3.3.4** + **Java 21**
- **Spring Cloud 2023.0.3** — Eureka, Gateway, OpenFeign
- **Spring Security 6** + **JJWT 0.12.3**
- **Spring Data JPA** + **H2** (in-memory, swap for PostgreSQL)
- **Springdoc OpenAPI 2.6.0** — Swagger UI per service
- **Lombok**, **Maven Multi-Module**
- **Docker Compose** + **MailHog** (local email)

---

## 🚀 Getting Started

### Option 1 — Run Locally (without Docker)

Start services **in order**:

```bash
# 1. Eureka (wait for it to be ready)
cd discovery-server && mvn spring-boot:run

# 2. API Gateway
cd api-gateway && mvn spring-boot:run

# 3. All other services (open separate terminals)
cd user-service        && mvn spring-boot:run
cd trip-service        && mvn spring-boot:run
cd booking-service     && mvn spring-boot:run
cd payment-service     && mvn spring-boot:run
cd notification-service && mvn spring-boot:run
```

### Option 2 — Docker Compose

```bash
# Build all services
mvn clean package -DskipTests

# Launch everything
docker-compose up --build
```

---

## 🔐 Authentication Flow

```
1. POST /api/v1/auth/register   → Create account
2. POST /api/v1/auth/login      → Returns { token, userId, email, role }
3. All other requests           → Add header: Authorization: Bearer <token>
```

---

## 📡 API Endpoints (via Gateway on port 8080)

### Auth (public)
| Method | Endpoint                    | Description        |
|--------|-----------------------------|--------------------|
| POST   | `/api/v1/auth/register`     | Register new user  |
| POST   | `/api/v1/auth/login`        | Login → JWT token  |

### Users (requires JWT)
| Method | Endpoint              | Description            |
|--------|-----------------------|------------------------|
| GET    | `/api/v1/users`       | List all (ADMIN only)  |
| GET    | `/api/v1/users/{id}`  | Get user by ID         |
| PUT    | `/api/v1/users/{id}`  | Update profile         |
| DELETE | `/api/v1/users/{id}`  | Delete (ADMIN only)    |

### Trips
| Method | Endpoint                           | Description                  |
|--------|------------------------------------|------------------------------|
| GET    | `/api/v1/trips`                    | List all trips               |
| GET    | `/api/v1/trips/available`          | List available trips         |
| GET    | `/api/v1/trips/{id}`               | Get trip by ID               |
| POST   | `/api/v1/trips`                    | Create trip                  |
| PUT    | `/api/v1/trips/{id}`               | Update trip                  |
| DELETE | `/api/v1/trips/{id}`               | Delete trip                  |
| GET    | `/api/v1/flights/search?origin=&destination=` | Search flights  |
| GET    | `/api/v1/hotels/search?city=`      | Search hotels by city        |
| GET    | `/api/v1/destinations/search?country=` | Search by country       |

### Bookings
| Method | Endpoint                        | Description            |
|--------|---------------------------------|------------------------|
| POST   | `/api/v1/bookings`              | Create booking         |
| GET    | `/api/v1/bookings/{id}`         | Get booking by ID      |
| GET    | `/api/v1/bookings/user/{userId}`| User's bookings        |
| PUT    | `/api/v1/bookings/{id}/cancel`  | Cancel booking         |

### Payments
| Method | Endpoint                           | Description               |
|--------|------------------------------------|---------------------------|
| POST   | `/api/v1/payments`                 | Process payment           |
| GET    | `/api/v1/payments/{id}`            | Get payment by ID         |
| GET    | `/api/v1/payments/booking/{id}`    | Payment for a booking     |
| GET    | `/api/v1/payments/user/{userId}`   | User's payment history    |

### Notifications
| Method | Endpoint                              | Description           |
|--------|---------------------------------------|-----------------------|
| POST   | `/api/v1/notifications/send`          | Send email            |
| GET    | `/api/v1/notifications`               | All notifications     |
| GET    | `/api/v1/notifications/user/{userId}` | User's notifications  |

---

## 📖 Swagger UI

Each service exposes its own Swagger UI:

| Service              | URL                                         |
|----------------------|---------------------------------------------|
| User Service         | http://localhost:8081/swagger-ui.html       |
| Trip Service         | http://localhost:8082/swagger-ui.html       |
| Booking Service      | http://localhost:8083/swagger-ui.html       |
| Payment Service      | http://localhost:8084/swagger-ui.html       |
| Notification Service | http://localhost:8085/swagger-ui.html       |
| Eureka Dashboard     | http://localhost:8761                       |
| MailHog UI           | http://localhost:8025                       |

---

## 🗄️ H2 Console (dev only)

| Service       | URL                                                    |
|---------------|--------------------------------------------------------|
| User DB       | http://localhost:8081/h2-console  (JDBC: `jdbc:h2:mem:userdb`)     |
| Trip DB       | http://localhost:8082/h2-console  (JDBC: `jdbc:h2:mem:tripdb`)     |
| Booking DB    | http://localhost:8083/h2-console  (JDBC: `jdbc:h2:mem:bookingdb`)  |
| Payment DB    | http://localhost:8084/h2-console  (JDBC: `jdbc:h2:mem:paymentdb`)  |

---

## 🔄 Typical User Flow

```
Register → Login (get JWT)
    → Create Trip (admin)
        → Create Booking (user, calls trip-service to validate + decrement seats)
            → Process Payment (calls booking-service to confirm)
                → Send Notification (email confirmation)
```

---

## ⚙️ Configuration

### JWT Secret
All services that process JWT must share the same secret. Update in each service's `application.yml`:
```yaml
jwt:
  secret: your-256-bit-secret-here
  expiration: 86400000  # 24h
```

### Switch to PostgreSQL
Replace H2 config in any service with:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/userdb
    username: postgres
    password: your-password
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      ddl-auto: update
```

---

## 📦 Build All Services

```bash
cd ease-travel-microservices
mvn clean install -DskipTests
```


