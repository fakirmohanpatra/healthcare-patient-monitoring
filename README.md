# Healthcare Patient Monitoring System

A Spring Boot-based healthcare patient monitoring application that provides secure patient vital tracking and management capabilities.

## 📋 Overview

This application is designed to manage and monitor patient vitals in a healthcare environment. It features a dual-database architecture using PostgreSQL for user management and MongoDB for storing patient vital signs data.

## 🚀 Features

- **User Authentication & Authorization**: Secure JWT-based authentication with role-based access control
- **Patient Vital Monitoring**: Track and store various patient vital signs (heart rate, blood pressure, temperature, etc.)
- **RESTful API**: Well-structured REST endpoints for all operations
- **Dual Database Architecture**: 
  - PostgreSQL for relational user data
  - MongoDB for flexible vital signs storage
- **API Documentation**: Integrated Swagger/OpenAPI documentation
- **Security**: Spring Security integration with JWT tokens

## 🛠️ Technology Stack

- **Framework**: Spring Boot 4.0.1
- **Java Version**: 17
- **Databases**:
  - PostgreSQL (User management)
  - MongoDB (Patient vitals storage)
- **Security**: Spring Security + JWT
- **API Documentation**: Swagger/OpenAPI
- **Build Tool**: Maven
- **Additional**:
  - Spring Data JPA
  - Spring Data MongoDB
  - Spring Boot Actuator

## 📋 Prerequisites

Before running this application, ensure you have the following installed:

- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+ (running on `localhost:5432`)
- MongoDB 4.4+ (running on `localhost:27017`)

## 🔧 Setup Instructions

### 1. Clone the Repository

```bash
git clone <repository-url>
cd healthcare
```

### 2. Database Setup

#### PostgreSQL Setup
```sql
-- Create database
CREATE DATABASE healthcare_db;

-- Database will be automatically populated on first run
```

#### MongoDB Setup
```bash
# Start MongoDB service
# MongoDB will automatically create the database on first connection
```

### 3. Configure Application Properties

Update `src/main/resources/application.properties` with your database credentials:

```properties
# PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/healthcare_db
spring.datasource.username=your_postgres_username
spring.datasource.password=your_postgres_password

# MongoDB
spring.mongodb.uri=mongodb://localhost:27017/healthcare_db

# JWT Secret (Change this in production!)
jwt.secret=healthcare-jwt-secret-key-must-be-at-least-256-bits-long-for-security
jwt.expiration-ms=3600000
```

### 4. Build the Application

```bash
./mvnw clean install
```

Or on Windows:
```bash
mvnw.cmd clean install
```

### 5. Run the Application

```bash
./mvnw spring-boot:run
```

Or on Windows:
```bash
mvnw.cmd spring-boot:run
```

The application will start on `http://localhost:8080`

## 📚 API Documentation

Once the application is running, access the Swagger UI documentation at:

```
http://localhost:8080/swagger-ui.html
```

## 🔐 Authentication

The application uses JWT-based authentication. To access protected endpoints:

1. **Register/Login**: Use the `/api/auth/login` endpoint to authenticate
2. **Get JWT Token**: The response will include a JWT token
3. **Use Token**: Include the token in subsequent requests:
   ```
   Authorization: Bearer <your-jwt-token>
   ```

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/example/healthcare/
│   │   ├── HealthcareApplication.java          # Main application class
│   │   ├── config/
│   │   │   ├── SecurityConfig.java             # Security configuration
│   │   │   └── SwaggerConfig.java              # API documentation config
│   │   ├── controller/
│   │   │   ├── AuthController.java             # Authentication endpoints
│   │   │   ├── PatientVitalController.java     # Patient vitals endpoints
│   │   │   └── dto/
│   │   │       └── LoginRequest.java           # Login request DTO
│   │   ├── domain/
│   │   │   ├── security/
│   │   │   │   ├── Role.java                   # User role enum
│   │   │   │   └── User.java                   # User entity (PostgreSQL)
│   │   │   └── vitals/
│   │   │       ├── PatientVital.java           # Patient vital entity (MongoDB)
│   │   │       └── VitalType.java              # Vital type enum
│   │   ├── repository/
│   │   │   ├── jpa/
│   │   │   │   └── UserRepository.java         # User repository (JPA)
│   │   │   └── mongo/
│   │   │       └── PatientVitalRepository.java # Vitals repository (MongoDB)
│   │   ├── service/
│   │   │   ├── CustomUserDetailsService.java   # User authentication service
│   │   │   ├── PatientVitalService.java        # Vitals service interface
│   │   │   └── PatientVitalServiceImpl.java    # Vitals service implementation
│   │   └── utils/
│   │       └── security/
│   │           ├── JwtAuthenticationFilter.java # JWT filter
│   │           └── JwtUtil.java                 # JWT utility
│   └── resources/
│       └── application.properties               # Application configuration
└── test/
    └── java/com/example/healthcare/
        └── HealthcareApplicationTests.java      # Unit tests
```

## 🔑 Key Endpoints

### Authentication
- `POST /api/auth/login` - User login
- `POST /api/auth/register` - User registration (if implemented)

### Patient Vitals
- `GET /api/vitals` - Get all patient vitals
- `GET /api/vitals/{id}` - Get specific vital by ID
- `POST /api/vitals` - Create new patient vital record
- `PUT /api/vitals/{id}` - Update patient vital
- `DELETE /api/vitals/{id}` - Delete patient vital

## 🧪 Running Tests

```bash
./mvnw test
```

Or on Windows:
```bash
mvnw.cmd test
```

## 🔒 Security Considerations

- **JWT Secret**: Change the JWT secret key in production to a secure random value
- **Database Passwords**: Use environment variables or secure vault systems for database credentials
- **HTTPS**: Enable HTTPS in production environments
- **Role-Based Access**: Implement appropriate role-based access controls for different user types

## 📊 Health Check

Spring Boot Actuator provides health check endpoints:

```
http://localhost:8080/actuator/health
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📧 Contact

For questions or support, please contact the development team.

## 🚧 Future Enhancements

- [ ] Real-time vital monitoring with WebSocket
- [ ] Advanced analytics and reporting
- [ ] Integration with medical devices
- [ ] Mobile application support
- [ ] Patient portal
- [ ] Alert system for critical vitals
- [ ] Multi-language support
