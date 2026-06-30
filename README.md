# AtlasAI - Intelligent Travel Platform

**AtlasAI** transforms travel planning through AI-powered itinerary recommendations and a scalable microservices architecture. The platform integrates booking, payments, notifications, caching, and secure authentication into a unified travel ecosystem. Built with Spring Boot, React, Kafka, Redis, and Docker to support modern cloud-native deployment patterns.

## ✨ Key Features

• AI-powered travel itinerary generation
• Flight and hotel search
• Booking and payment processing
• Event-driven architecture using Kafka
• Redis-based caching
• JWT authentication and authorization
• Containerized deployment with Docker

## 🛠️ Tech Stack

**Backend:** Spring Boot (Microservices)
**Frontend:** React
**AI Services:** FastAPI
**Message Queue:** Apache Kafka
**Caching:** Redis
**Containerization:** Docker

## 📋 Table of Contents

- [Project Overview](#project-overview)
- [Architecture](#architecture)
- [Data Flow](#data-flow)
- [Tech Stack](#tech-stack)
- [Microservices](#microservices)
- [Quick Start](#quick-start)
- [Project Structure](#project-structure)
- [Configuration](#configuration)
- [Docker Deployment](#docker-deployment)
- [API Documentation](#api-documentation)

## 🎯 Project Overview

**AtlasAi** is a distributed travel management system that enables users to:
- Register and manage user accounts
- Book trips and accommodations
- Process payments securely
- Receive real-time notifications
- Track bookings and history
- Manage user profiles

## 🏗️ Architecture

The system follows a microservices architecture pattern with the following components:

```
┌─────────────────────────────────────────────────────────────┐
│                     Frontend (React)                         │
│              (Vite + Modern JavaScript/JSX)                 │
└────────────────────┬────────────────────────────────────────┘
                     │
                     │ HTTP/REST
                     ▼
┌─────────────────────────────────────────────────────────────┐
│                  API Gateway (Port 8080)                     │
│          (Routes requests to microservices)                  │
└────────┬────────────┬────────────┬────────────┬──────────────┘
         │            │            │            │
    ┌────▼──┐  ┌─────▼───┐  ┌────▼───┐  ┌────▼──────┐
    │ User  │  │ Booking │  │ Trip   │  │ Payment   │
    │Service│  │ Service │  │Service │  │ Service   │
    │(8081) │  │ (8082)  │  │(8083)  │  │ (8084)    │
    └───┬───┘  └────┬────┘  └────┬───┘  └────┬──────┘
        │           │            │           │
    ┌───▼───────────▼────────────▼───────────▼───┐
    │      Discovery Server (Eureka) (8761)      │
    │  (Service Registry & Discovery)            │
    └────────────────────────────────────────────┘
        │
    ┌───▼─────────────────────────────────────────┐
    │  Notification Service (8085)                │
    │  (Email/SMS notifications)                  │
    └──────────────────────────────────────────────┘
```

### Microservices Components

1. **API Gateway** (Port 8080)
   - Entry point for all frontend requests
   - Request routing and load balancing
   - Authentication token validation
   - Request/response filtering

2. **Discovery Server** (Eureka) (Port 8761)
   - Service registry
   - Service discovery
   - Health monitoring
   - Load balancing support

3. **User Service** (Port 8081)
   - User registration and authentication
   - Profile management
   - User data persistence

4. **Booking Service** (Port 8082)
   - Booking creation and management
   - Booking history
   - Booking status tracking

5. **Trip Service** (Port 8083)
   - Trip planning and management
   - Trip details storage
   - Trip itinerary management

6. **Payment Service** (Port 8084)
   - Payment processing
   - Transaction management
   - Payment status tracking

7. **Notification Service** (Port 8085)
   - Email notifications
   - SMS notifications
   - Event-driven notifications
   - Notification history

## 📊 Data Flow

### User Registration Flow

```
Frontend → API Gateway → User Service → Database
     ↓
Notification Service (Send Welcome Email)
```

### Booking Flow

```
Frontend → API Gateway → Booking Service → Database
                              ↓
                         Payment Service (Initialize Payment)
                              ↓
                         Notification Service (Send Confirmation)
```

### Payment Processing Flow

```
Frontend → API Gateway → Payment Service → Payment Gateway
                              ↓
                         Booking Service (Update Status)
                              ↓
                         Notification Service (Send Receipt)
```

**For detailed data flow diagrams, see [DATA_FLOW.md](DATA_FLOW.md)**  
**For complete architecture documentation, see [ARCHITECTURE.md](ARCHITECTURE.md)**

## 🛠️ Tech Stack

### Backend
- **Framework**: Spring Boot 3.x
- **Cloud**: Spring Cloud (Eureka, Config Server)
- **Service Discovery**: Eureka
- **Build Tool**: Maven
- **Language**: Java 17+
- **Database**: MySQL/PostgreSQL
- **ORM**: Spring Data JPA

### Frontend
- **Framework**: React 18+
- **Build Tool**: Vite
- **HTTP Client**: Axios
- **State Management**: React Context API
- **Styling**: CSS3

### Infrastructure
- **Containerization**: Docker
- **Orchestration**: Docker Compose
- **API Communication**: REST (HTTP)

## 🗂️ Microservices

### User Service
- **Port**: 8081
- **Base URL**: `http://localhost:8081`
- **Key Endpoints**:
  - POST `/api/users/register` - Register new user
  - POST `/api/users/login` - User login
  - GET `/api/users/{id}` - Get user details
  - PUT `/api/users/{id}` - Update user profile

### Booking Service
- **Port**: 8082
- **Base URL**: `http://localhost:8082`
- **Key Endpoints**:
  - POST `/api/bookings` - Create booking
  - GET `/api/bookings/{id}` - Get booking details
  - GET `/api/bookings/user/{userId}` - Get user bookings
  - PUT `/api/bookings/{id}` - Update booking
  - DELETE `/api/bookings/{id}` - Cancel booking

### Trip Service
- **Port**: 8083
- **Base URL**: `http://localhost:8083`
- **Key Endpoints**:
  - POST `/api/trips` - Create trip
  - GET `/api/trips/{id}` - Get trip details
  - GET `/api/trips` - List trips
  - PUT `/api/trips/{id}` - Update trip
  - DELETE `/api/trips/{id}` - Delete trip

### Payment Service
- **Port**: 8084
- **Base URL**: `http://localhost:8084`
- **Key Endpoints**:
  - POST `/api/payments` - Process payment
  - GET `/api/payments/{id}` - Get payment details
  - GET `/api/payments/booking/{bookingId}` - Get booking payments
  - PUT `/api/payments/{id}` - Update payment status

### Notification Service
- **Port**: 8085
- **Base URL**: `http://localhost:8085`
- **Key Endpoints**:
  - POST `/api/notifications/send` - Send notification
  - GET `/api/notifications/{userId}` - Get user notifications
  - PUT `/api/notifications/{id}/read` - Mark as read
  - DELETE `/api/notifications/{id}` - Delete notification

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.8.1 or higher
- Node.js 16+ and npm
- Docker and Docker Compose (optional)
- MySQL 8.0 or PostgreSQL 12+

### Setup Instructions

#### 1. Clone Repository
```bash
git clone https://github.com/adarshrajsinha25/trip-hub.git
cd trip-hub
```

#### 2. Backend Setup

##### Start Services Individually

```bash
# Start Discovery Server (Eureka)
cd backend/discovery-server
mvn spring-boot:run

# In another terminal - Start API Gateway
cd backend/api-gateway
mvn spring-boot:run

# In another terminal - Start User Service
cd backend/user-service
mvn spring-boot:run

# In another terminal - Start Booking Service
cd backend/booking-service
mvn spring-boot:run

# In another terminal - Start Trip Service
cd backend/trip-service
mvn spring-boot:run

# In another terminal - Start Payment Service
cd backend/payment-service
mvn spring-boot:run

# In another terminal - Start Notification Service
cd backend/notification-service
mvn spring-boot:run
```

##### Using Docker Compose
```bash
cd backend
docker-compose up -d
```

#### 3. Frontend Setup
```bash
cd frontend
npm install
npm run dev
```

Access the application at `http://localhost:5173`

## 📁 Project Structure

```
trip-hub/
├── backend/
│   ├── api-gateway/              # API Gateway Service
│   │   ├── src/
│   │   └── pom.xml
│   ├── booking-service/          # Booking Management Service
│   │   ├── src/
│   │   └── pom.xml
│   ├── discovery-server/         # Eureka Service Discovery
│   │   ├── src/
│   │   └── pom.xml
│   ├── notification-service/     # Notification Service
│   │   ├── src/
│   │   └── pom.xml
│   ├── payment-service/          # Payment Processing Service
│   │   ├── src/
│   │   └── pom.xml
│   ├── trip-service/             # Trip Management Service
│   │   ├── src/
│   │   └── pom.xml
│   ├── user-service/             # User Management Service
│   │   ├── src/
│   │   └── pom.xml
│   ├── docker-compose.yml        # Docker Compose Configuration
│   ├── Dockerfile                # Docker Image Configuration
│   ├── pom.xml                   # Parent POM
│   └── README.md
├── frontend/
│   ├── src/
│   │   ├── api/                  # API integration modules
│   │   ├── components/           # React components
│   │   ├── pages/                # Page components
│   │   ├── context/              # React Context
│   │   └── utils/                # Utility functions
│   ├── package.json
│   ├── vite.config.js
│   ├── index.html
│   └── README.md
├── README.md                     # Project Documentation
├── ARCHITECTURE.md               # Architecture Diagrams
└── DATA_FLOW.md                  # Data Flow Documentation
```

## ⚙️ Configuration

### Backend Configuration

Each microservice has an `application.yml` file in `src/main/resources/`:

#### API Gateway Configuration
```yaml
server:
  port: 8080
spring:
  application:
    name: api-gateway
  cloud:
    gateway:
      routes:
        - id: user-service
          uri: lb://user-service
          predicates:
            - Path=/api/users/**
```

#### Service Configuration
```yaml
server:
  port: 8081
spring:
  application:
    name: user-service
  jpa:
    hibernate:
      ddl-auto: update
  datasource:
    url: jdbc:mysql://localhost:3306/ease_travel_user
    username: root
    password: your_password
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
```

### Frontend Configuration

Edit `src/config.js` to update API endpoints:

```javascript
const API_BASE_URL = process.env.VITE_API_URL || 'http://localhost:8080/api';

export default {
  API_BASE_URL,
  TIMEOUT: 10000
};
```

## 🐳 Docker Deployment

### Build Docker Images
```bash
cd backend
docker build -t trip-hub-services:latest .
```

### Run with Docker Compose
```bash
cd backend
docker-compose up -d
```

### Check Service Status
```bash
docker-compose ps
```

### Stop Services
```bash
docker-compose down
```

## 📚 API Documentation

### Authentication

Most API endpoints require JWT authentication. Include the token in the Authorization header:

```
Authorization: Bearer <jwt_token>
```

### Example API Calls

#### Register User
```bash
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "password123",
    "fullName": "John Doe"
  }'
```

#### Login
```bash
curl -X POST http://localhost:8080/api/users/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "password123"
  }'
```

#### Create Booking
```bash
curl -X POST http://localhost:8080/api/bookings \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "tripId": 1,
    "userId": 1,
    "numberOfPeople": 2,
    "totalCost": 5000
  }'
```

## 🔐 Security Considerations

- JWT tokens are used for API authentication
- Passwords are hashed using bcrypt
- API Gateway validates all incoming requests
- CORS is configured for frontend communication
- Database credentials should be stored in environment variables
- Use HTTPS in production

## 🤝 Contributing

1. Create a feature branch (`git checkout -b feature/amazing-feature`)
2. Commit changes (`git commit -m 'Add amazing feature'`)
3. Push to branch (`git push origin feature/amazing-feature`)
4. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👥 Authors

- Adarsh Raj Sinha

## 📞 Support

For support, please create an issue in the GitHub repository or contact the development team.

---

**Last Updated**: June 2026  
**Version**: 1.0.0
