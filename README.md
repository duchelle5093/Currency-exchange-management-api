# Currency Conversion API

A Spring Boot-based API that converts money between different currencies using real-time exchange rates. The API retrieves current exchange rates dynamically from the ExchangeRate API and provides a simple, efficient way to perform currency conversions.

## Table of Contents

- [Features](#features)
- [Technology Stack](#technology-stack)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation & Setup](#installation--setup)
- [API Endpoints](#api-endpoints)
- [Example Requests & Responses](#example-requests--responses)
- [Error Handling](#error-handling)
- [Extending the API](#extending-the-api)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## Features

- **Currency Conversion:** Convert any amount from one currency to another using real-time exchange rates
- **Multiple Endpoints:** Support for both POST (JSON body) and GET (path variables) conversion formats
- **Real-time Exchange Rates:** Integration with ExchangeRate API for up-to-date conversion rates
- **Supported Currencies:** Access to all major global currencies
- **Robust Error Handling:** Comprehensive error reporting for invalid requests or API issues
- **Reactive Programming:** Non-blocking API calls using Spring WebFlux and Project Reactor

## Technology Stack

- **Java:** 21
- **Spring Boot:** 3.4.x for REST API development
- **Spring WebFlux:** For reactive programming and non-blocking API calls
- **Project Reactor:** Reactive streams implementation
- **Lombok:** Reducing boilerplate code
- **ExchangeRate API:** External service for current exchange rates
- **Jakarta Validation:** Input validation

## Getting Started

### Prerequisites

- Java 21 or later installed
- Maven (or use the Maven Wrapper provided)
- An IDE such as IntelliJ IDEA or Eclipse
- API key from [ExchangeRate API](https://www.exchangerate-api.com/) (free tier available)

### Installation & Setup

2. **Configure the ExchangeRate API key:**

   Modify `src/main/resources/application.properties` with your ExchangeRate API key:

   ```properties
   exchangerate.api.base-url=https://v6.exchangerate-api.com
   exchangerate.api.key=your_api_key
   ```

3. **Build the project using Maven:**

   ```bash
   mvn clean install
   ```

4. **Run the application:**

   ```bash
   mvn spring-boot:run
   ```

   The application will start on port 8080 by default.

## API Endpoints

The API provides the following endpoints for currency conversion:

### 1. Convert Currency (POST)

- **Endpoint:** `POST /api/currency/convert`
- **Content-Type:** `application/json`
- **Description:** Converts an amount from one currency to another using a JSON request body

### 2. Convert Currency (GET)

- **Endpoint:** `GET /api/currency/convert/{sourceCurrency}/to/{targetCurrency}?amount={amount}`
- **Description:** Alternative endpoint for currency conversion using path variables and query parameter

### 3. Get Supported Currencies

- **Endpoint:** `GET /api/currency/supported-currencies`
- **Description:** Returns a list of all supported currency codes

## Example Requests & Responses

### Example 1: Convert USD to EUR (POST method)

**Request:**
```bash
curl -X POST "http://localhost:8080/api/currency/convert" \
-H "Content-Type: application/json" \
-d '{
    "sourceCurrency": "USD",
    "targetCurrency": "EUR",
    "amount": 100
}'
```

**Response:**
```json
{
  "sourceCurrency": "USD",
  "targetCurrency": "EUR",
  "sourceAmount": 100.0,
  "targetAmount": 91.68,
  "exchangeRate": 0.9168,
  "timestamp": "2025-05-02T10:15:30.123"
}
```

### Example 2: Convert USD to EUR (GET method)

**Request:**
```bash
curl -X GET "http://localhost:8080/api/currency/convert/USD/to/EUR?amount=100"
```

**Response:**
Same as above.

### Example 3: Get Supported Currencies

**Request:**
```bash
curl -X GET "http://localhost:8080/api/currency/supported-currencies"
```

**Response:**
```json
["USD", "EUR", "GBP", "JPY", "AUD", "CAD", "CHF", "CNY", "INR"]
```

## Error Handling

The API provides comprehensive error handling with descriptive messages. Here are some common error scenarios:

### Invalid Amount

**Request:**
```bash
curl -X POST "http://localhost:8080/api/currency/convert" \
-H "Content-Type: application/json" \
-d '{
    "sourceCurrency": "USD",
    "targetCurrency": "EUR",
    "amount": -100
}'
```

**Response:**
```json
{
  "status": 400,
  "message": "Amount must be greater than zero",
  "timestamp": 1714640530123
}
```

### Invalid Currency Code

**Request:**
```bash
curl -X POST "http://localhost:8080/api/currency/convert" \
-H "Content-Type: application/json" \
-d '{
    "sourceCurrency": "USD",
    "targetCurrency": "XYZ",
    "amount": 100
}'
```

**Response:**
```json
{
  "status": 400,
  "message": "Target currency XYZ not found in available rates",
  "timestamp": 1714640530123
}
```

### External API Error

**Response:**
```json
{
  "status": 500,
  "message": "Error communicating with exchange rate service",
  "timestamp": 1714640530123
}
```

## Extending the API

You can extend this API in several ways:

1. **Caching Exchange Rates:** Implement a caching mechanism to reduce API calls and improve performance
2. **Historical Rates:** Add support for historical exchange rates
3. **Conversion Calculator:** Create a frontend that uses this API to build a conversion calculator
4. **Currency Alerts:** Implement a notification system for significant rate changes

## Contributing

Contributions are welcome! Feel free to fork the repository, create a new branch for your feature or bug fix, and submit a pull request. For major changes, please open an issue first to discuss what you would like to change.

## License

This project is licensed under the MIT License. See the LICENSE file for more details.

## Contact

