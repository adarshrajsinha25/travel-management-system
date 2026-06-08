# Ease Travel - Data Flow Documentation

## Complete Data Flow Diagrams

### 1. User Registration and Authentication Flow

```mermaid
sequenceDiagram
    participant User as User
    participant Frontend as React Frontend
    participant Gateway as API Gateway
    participant UserSvc as User Service
    participant UserDB as User Database
    participant NotifSvc as Notification Service
    
    User->>Frontend: Enter Registration Details
    Frontend->>Gateway: POST /api/users/register
    Gateway->>UserSvc: Route to User Service
    UserSvc->>UserDB: Check Email Exists
    UserDB-->>UserSvc: Email Not Found
    UserSvc->>UserDB: Save User (Hashed Password)
    UserDB-->>UserSvc: User Created
    UserSvc->>NotifSvc: Publish UserRegistered Event
    NotifSvc->>NotifSvc: Send Welcome Email
    UserSvc-->>Gateway: Registration Success + JWT
    Gateway-->>Frontend: 200 OK + Token
    Frontend-->>User: Show Success Message
```

### 2. User Login Flow

```mermaid
sequenceDiagram
    participant User as User
    participant Frontend as React Frontend
    participant Gateway as API Gateway
    participant UserSvc as User Service
    participant UserDB as User Database
    
    User->>Frontend: Enter Email & Password
    Frontend->>Gateway: POST /api/users/login
    Gateway->>UserSvc: Route to User Service
    UserSvc->>UserDB: Find User by Email
    UserDB-->>UserSvc: User Found
    UserSvc->>UserSvc: Verify Password Hash
    Note over UserSvc: Password matches
    UserSvc->>UserSvc: Generate JWT Token
    UserSvc-->>Gateway: Login Success + JWT
    Gateway-->>Frontend: 200 OK + Token
    Frontend->>Frontend: Store Token (localStorage)
    Frontend-->>User: Redirect to Dashboard
```

### 3. Create Booking Flow

```mermaid
sequenceDiagram
    participant User as User
    participant Frontend as React Frontend
    participant Gateway as API Gateway
    participant BookingSvc as Booking Service
    participant BookingDB as Booking Database
    participant PaymentSvc as Payment Service
    participant TripSvc as Trip Service
    participant NotifSvc as Notification Service
    
    User->>Frontend: Select Trip & Click Book
    Frontend->>Frontend: Prepare Booking Data
    Frontend->>Gateway: POST /api/bookings (with JWT)
    Gateway->>Gateway: Validate JWT Token
    Gateway->>BookingSvc: Route to Booking Service
    BookingSvc->>BookingSvc: Validate Booking Data
    BookingSvc->>TripSvc: Fetch Trip Details
    TripSvc-->>BookingSvc: Trip Details Received
    BookingSvc->>BookingDB: Create Booking Record
    BookingDB-->>BookingSvc: Booking Created (ID: 123)
    BookingSvc->>BookingSvc: Publish BookingCreated Event
    BookingSvc->>PaymentSvc: Initialize Payment
    BookingSvc->>NotifSvc: Publish BookingConfirmed Event
    NotifSvc->>NotifSvc: Send Confirmation Email
    BookingSvc-->>Gateway: 201 Created
    Gateway-->>Frontend: Success Response
    Frontend-->>User: Show Booking Confirmation
```

### 4. Payment Processing Flow

```mermaid
sequenceDiagram
    participant User as User
    participant Frontend as React Frontend
    participant Gateway as API Gateway
    participant PaymentSvc as Payment Service
    participant PaymentDB as Payment Database
    participant BookingSvc as Booking Service
    participant ExternalGateway as Payment Gateway<br/>Stripe/PayPal
    participant NotifSvc as Notification Service
    
    User->>Frontend: Enter Payment Details
    Frontend->>Gateway: POST /api/payments (with JWT)
    Gateway->>PaymentSvc: Route to Payment Service
    PaymentSvc->>PaymentSvc: Validate Amount
    PaymentSvc->>ExternalGateway: Process Payment
    ExternalGateway-->>PaymentSvc: Payment Success/Failure
    alt Payment Successful
        PaymentSvc->>PaymentDB: Save Transaction Record
        PaymentDB-->>PaymentSvc: Record Saved
        PaymentSvc->>BookingSvc: Update Booking Status (Paid)
        BookingSvc->>BookingDB: Update Status to CONFIRMED
        PaymentSvc->>NotifSvc: Publish PaymentProcessed Event
        NotifSvc->>NotifSvc: Send Payment Receipt
        PaymentSvc-->>Gateway: 200 OK
    else Payment Failed
        PaymentSvc-->>Gateway: 402 Payment Failed
    end
    Gateway-->>Frontend: Response
    Frontend-->>User: Show Result
```

### 5. Trip Management Flow

```mermaid
sequenceDiagram
    participant User as User
    participant Frontend as React Frontend
    participant Gateway as API Gateway
    participant TripSvc as Trip Service
    participant TripDB as Trip Database
    
    User->>Frontend: Create/View Trips
    Frontend->>Gateway: GET/POST /api/trips (with JWT)
    Gateway->>Gateway: Validate JWT
    Gateway->>TripSvc: Route to Trip Service
    alt Create Trip
        TripSvc->>TripDB: Save Trip Details
        TripDB-->>TripSvc: Trip Created
        TripSvc-->>Gateway: 201 Created
    else View Trip
        TripSvc->>TripDB: Query Trip (ID: X)
        TripDB-->>TripSvc: Trip Details
        TripSvc-->>Gateway: 200 OK
    else Update Trip
        TripSvc->>TripDB: Update Trip
        TripDB-->>TripSvc: Updated
        TripSvc-->>Gateway: 200 OK
    end
    Gateway-->>Frontend: Response
    Frontend-->>User: Display Trips
```

### 6. Notification System Flow

```mermaid
sequenceDiagram
    participant Services as Microservices<br/>User/Booking/Payment
    participant EventQueue as Event Queue<br/>In-Memory
    participant NotifSvc as Notification Service
    participant NotifDB as Notification Database
    participant EmailSvc as Email Service<br/>SMTP
    participant User as User
    
    Services->>EventQueue: Publish Event<br/>UserRegistered/BookingCreated<br/>PaymentProcessed
    EventQueue->>NotifSvc: Consume Event
    NotifSvc->>NotifDB: Create Notification Record
    NotifDB-->>NotifSvc: Record Saved
    NotifSvc->>EmailSvc: Send Email
    EmailSvc-->>User: Email Delivered
    NotifSvc->>NotifDB: Mark as Sent
```

### 7. Service Discovery Flow

```mermaid
sequenceDiagram
    participant Services as Microservices<br/>Startup
    participant Eureka as Eureka Server<br/>Service Registry
    participant Gateway as API Gateway
    participant Client as Client Request
    
    Services->>Eureka: Register Service<br/>Name + Instance Info
    Eureka->>Eureka: Store Service Metadata
    Eureka-->>Services: Registration Confirmed
    Note over Eureka: Services Register:<br/>- user-service:8081<br/>- booking-service:8082<br/>- trip-service:8083<br/>- payment-service:8084<br/>- notification-service:8085
    
    Client->>Gateway: API Request
    Gateway->>Eureka: Query: Where is user-service?
    Eureka-->>Gateway: user-service at 192.168.1.5:8081
    Gateway->>Services: Forward Request
```

## API Gateway Request Flow

```mermaid
graph TD
    A["Client Request"] -->|HTTP/REST| B["API Gateway"]
    B -->|1. Extract JWT| C{"Token Valid?"}
    C -->|No| D["Return 401 Unauthorized"]
    C -->|Yes| E["Parse JWT Claims"]
    E -->|2. Match Route| F{"Route Exists?"}
    F -->|No| G["Return 404 Not Found"]
    F -->|Yes| H{"CORS Check"}
    H -->|Fail| I["Return 403 Forbidden"]
    H -->|Pass| J{"Rate Limit OK?"}
    J -->|Exceeded| K["Return 429 Too Many Requests"]
    J -->|OK| L["3. Forward to Service"]
    L -->|Discover Service| M["Query Eureka"]
    M -->|Service Address| N["Route Request"]
    N -->|Service Response| O["Add CORS Headers"]
    O -->|Response| P["Client Response"]
    D --> P
    G --> P
    I --> P
    K --> P
```

## Database Interaction Pattern

```mermaid
graph TB
    subgraph Services["Microservices"]
        US["User Service<br/>8081"]
        BS["Booking Service<br/>8082"]
        TS["Trip Service<br/>8083"]
        PS["Payment Service<br/>8084"]
    end
    
    subgraph Databases["Isolated Databases"]
        UDB["User DB<br/>users<br/>profiles<br/>preferences"]
        BDB["Booking DB<br/>bookings<br/>booking_items<br/>status"]
        TDB["Trip DB<br/>trips<br/>itineraries<br/>destinations"]
        PDB["Payment DB<br/>transactions<br/>invoices<br/>receipts"]
    end
    
    US -->|CRUD| UDB
    BS -->|CRUD| BDB
    TS -->|CRUD| TDB
    PS -->|CRUD| PDB
    
    BS -.->|Query Service| US
    PS -.->|Query Service| BS
    PS -.->|Query Service| US
```

## Complete End-to-End User Journey

```mermaid
graph LR
    A["👤 User Opens App"] -->|1| B["React Frontend Loads"]
    B -->|2| C["Frontend Connects to API Gateway"]
    C -->|3| D["User Clicks Register"]
    D -->|4| E["POST /api/users/register"]
    E -->|5| F["User Service Processes"]
    F -->|6| G["Store in User DB"]
    G -->|7| H["Send Welcome Email"]
    H -->|8| I["Return JWT Token"]
    I -->|9| J["Frontend Stores Token"]
    J -->|10| K["User Logs In"]
    K -->|11| L["POST /api/users/login"]
    L -->|12| M["Verify Credentials"]
    M -->|13| N["Return JWT"]
    N -->|14| O["Redirect to Dashboard"]
    O -->|15| P["User Views Trips"]
    P -->|16| Q["GET /api/trips"]
    Q -->|17| R["Trip Service Queries DB"]
    R -->|18| S["Return Trip List"]
    S -->|19| T["User Selects Trip & Books"]
    T -->|20| U["POST /api/bookings"]
    U -->|21| V["Booking Service Creates Record"]
    V -->|22| W["Process Payment"]
    W -->|23| X["Payment Service Charges Card"]
    X -->|24| Y["Send Confirmation Email"]
    Y -->|25| Z["Display Booking Confirmation"]
```

## State Transitions

### Booking State Machine

```mermaid
stateDiagram-v2
    [*] --> PENDING: Create Booking
    PENDING --> PAYMENT_PENDING: Await Payment
    PAYMENT_PENDING --> CONFIRMED: Payment Success
    PAYMENT_PENDING --> CANCELLED: Payment Failed
    CONFIRMED --> COMPLETED: Trip Completed
    CONFIRMED --> CANCELLED: User Cancels
    CANCELLED --> [*]
    COMPLETED --> [*]
```

### Payment State Machine

```mermaid
stateDiagram-v2
    [*] --> INITIATED: Start Payment
    INITIATED --> PROCESSING: Send to Gateway
    PROCESSING --> SUCCESS: Payment Approved
    PROCESSING --> FAILED: Payment Declined
    SUCCESS --> [*]
    FAILED --> [*]
```

## Error Handling Flow

```mermaid
graph TD
    A["Request Received"] --> B{"Validate Input"}
    B -->|Invalid| C["400 Bad Request"]
    B -->|Valid| D{"Authenticate"}
    D -->|Failed| E["401 Unauthorized"]
    D -->|Success| F{"Authorize"}
    F -->|Denied| G["403 Forbidden"]
    F -->|Allowed| H{"Process Request"}
    H -->|Business Logic Error| I["422 Unprocessable Entity"]
    H -->|Resource Not Found| J["404 Not Found"]
    H -->|Success| K["200 OK / 201 Created"]
    H -->|Server Error| L["500 Internal Server Error"]
    
    C --> M["Return Error Response"]
    E --> M
    G --> M
    I --> M
    J --> M
    L --> M
    K --> M
```

---

**Version**: 1.0.0  
**Last Updated**: June 2026
