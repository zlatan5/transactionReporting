# Pismo Transaction Service

A transaction service implementation with two runnable options:

- Go (Gin + GORM, SQLite in-memory)
- Spring Boot (Java) — original implementation

## Features

- **Account Management**: Create and retrieve accounts with document numbers
- **Transaction Processing**: Handle different types of financial operations
- **Business Rules**: Automatic amount sign handling based on operation type
- **RESTful API**: Clean REST endpoints for all operations
- **Data Validation**: Comprehensive input validation
- **Exception Handling**: Global exception handling with proper HTTP status codes
- **Testing**: Unit and integration tests
- **Docker Support**: Containerized deployment

## Operation Types

1. **CASH PURCHASE** (ID: 1) - Recorded as negative amount
2. **INSTALLMENT PURCHASE** (ID: 2) - Recorded as negative amount  
3. **WITHDRAWAL** (ID: 3) - Recorded as negative amount
4. **PAYMENT** (ID: 4) - Recorded as positive amount

## API Endpoints

### Accounts

- `POST /api/v1/accounts` - Create a new account
- `GET /api/v1/accounts/{accountId}` - Get account by ID

### Transactions

- `POST /api/v1/transactions` - Create a new transaction

## Technology Stack

- Go 1.22, Gin, GORM, SQLite (in-memory)
- Java 17, Spring Boot 3.2.0, Spring Data JPA, H2 (in-memory)
- Docker / Docker Compose

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Docker (optional)

### Running the Application

#### Option A: Go service (recommended)

```bash
# From repo root
cd go-app
go mod tidy
go build ./cmd/server
./server
# Service runs at http://localhost:8080/api/v1
```

With Docker:

```bash
docker compose up --build
# go-transaction-service listens on 8080
```

#### Option B: Java Spring Boot

```bash
# From repo root
./mvnw spring-boot:run
```

### Accessing the Application

- **API Base URL**: `http://localhost:8080/api/v1`
- **H2 Database Console**: `http://localhost:8080/api/v1/h2-console`
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: `password`

## API Examples

### Create Account

```bash
curl -X POST http://localhost:8080/api/v1/accounts \
  -H "Content-Type: application/json" \
  -d '{
    "document_number": "12345678901"
  }'
```

### Create Transaction

```bash
curl -X POST http://localhost:8080/api/v1/transactions \
  -H "Content-Type: application/json" \
  -d '{
    "account_id": 1,
    "operation_type_id": 1,
    "amount": 50.00
  }'
```

### Get Account

```bash
curl http://localhost:8080/api/v1/accounts/1
```

## Testing

Run the test suite:

```bash
./mvnw test
```

## Project Structure (high level)

```
go-app/
  cmd/server/main.go
  internal/{db,handler,model,router,seed,service}

src/main/java/com/pismo/transaction/... (Spring Boot option)
```

## Database Schema

### Accounts Table
- `account_id` (Primary Key)
- `document_number` (Unique)

### Operation Types Table
- `operation_type_id` (Primary Key)
- `description`

### Transactions Table
- `transaction_id` (Primary Key)
- `account_id` (Foreign Key)
- `operation_type_id` (Foreign Key)
- `amount`
- `event_date`

## Business Rules

1. **Amount Sign Rules**:
   - Operations 1, 2, 3 (purchases/withdrawals): Always negative
   - Operation 4 (payment): Always positive
   - Input amount is converted to absolute value before applying sign rules

2. **Validation Rules**:
   - Document number: 11-14 characters, required
   - Account ID: Required, must exist
   - Operation Type ID: Required, must exist
   - Amount: Required, must be positive

## Notes

- Go service seeds operation types (1..4) on startup.
- Go uses in-memory SQLite shared cache.
- Compose runs the Go service by default (`go-transaction-service`).

## License

This project is licensed under the MIT License.
