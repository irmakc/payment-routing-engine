# Payment Routing Engine (Demo)

A Spring Boot demo project that simulates payment routing across multiple mock providers with fallback, attempt tracking, idempotency, and OpenAPI docs.

## Features
- Payment creation and processing flow
- Routing rules based on amount and currency
- Provider fallback mechanism
- Payment attempt history tracking
- Idempotency-Key support (duplicate request prevention)
- Correlation ID propagation (X-Correlation-Id)
- Structured logging with file output
- PostgreSQL + Flyway migrations
- Swagger / OpenAPI documentation
- Unit tests for routing and idempotency logic

### Core Components

- PaymentService → Handles payment lifecycle (create, idempotency, retrieval)

- PaymentProcessingService → Orchestrates routing, provider execution, fallback, and attempt tracking

- RoutingService → Determines provider order based on business rules

- PaymentAttempt → Stores each provider attempt result

- CorrelationIdFilter → Adds request tracing support

- Flyway → Manages schema migrations

### Tech Stack

- Java

- Spring Boot 4

- Spring Data JPA

- PostgreSQL

- Flyway

- Springdoc OpenAPI (Swagger UI)

- JUnit + Mockito

- Docker Compose

### How to Run
- Start PostgreSQL
docker compose up -d

- Run the application
./gradlew bootRun


Application runs on:

http://localhost:8080

### API Documentation

Swagger UI:

http://localhost:8080/swagger-ui/index.html


OpenAPI JSON:

http://localhost:8080/v3/api-docs

### Example Requests
Create and Process a Payment

curl -X POST "http://localhost:8080/payments" \
-H "Content-Type: application/json" \
-H "Idempotency-Key: demo-1" \
-H "X-Correlation-Id: corr-1" \
-d '{"amount":120.50,"currency":"EUR","customerId":"user-1"}'

Retrieve Payment Details (with Attempt History)
curl -X GET "http://localhost:8080/payments/{paymentId}"

### Idempotency Support

If the same Idempotency-Key is sent multiple times:

- A new payment will NOT be created

- The original payment result will be returned

- This prevents duplicate charges in case of network retries.

### Observability

Each request:

- Accepts X-Correlation-Id

- Generates one if not provided

- Propagates it to logs

- Returns it in response headers

Logs are written to:
logs/payment-routing.log

### Running Tests
./gradlew test


Test coverage includes:

- Routing decision logic

- Idempotency behavior

### Design Principles Demonstrated

- Separation of concerns

- Layered architecture

- Transactional boundaries

- Provider fallback pattern

- Idempotent API design

- Observability-first approach

- Clean error handling with domain exceptions

### Disclaimer

This project uses mock providers and simplified routing rules for demonstration purposes only.

It is not intended for production use.

