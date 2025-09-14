# Pismo Transaction Service

A Spring Boot application for managing financial transactions with different operation types.

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

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **H2 Database** (in-memory)
- **Maven**
- **Docker**

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Docker (optional)

### Running the Application

#### Using Maven

```bash
# Clone the repository
git clone <repository-url>
cd pismo-transaction-service

# Run the application
./mvnw spring-boot:run
```

#### Using Docker

```bash
# Build and run with Docker Compose
docker-compose up --build
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

## Project Structure

```
src/
├── main/
│   ├── java/com/pismo/transaction/
│   │   ├── PismoTransactionApplication.java
│   │   ├── config/
│   │   ├── controller/
│   │   ├── domain/
│   │   │   ├── entity/
│   │   │   ├── repository/
│   │   │   └── service/
│   │   ├── dto/
│   │   │   ├── request/
│   │   │   └── response/
│   │   ├── exception/
│   │   └── util/
│   └── resources/
│       ├── application.yml
│       └── data.sql
└── test/
    └── java/com/pismo/transaction/
        ├── controller/
        └── service/
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

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Ensure all tests pass
6. Submit a pull request

## License

This project is licensed under the MIT License.
