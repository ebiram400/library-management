
# Library Management System

This is an enhanced version of the **Library Management System** project, built using **Spring Boot**, **JPA**, and **Spring Security**. The system includes advanced security measures, a layered architecture, and various features to manage books and user operations effectively.

## Features

### 1. **Spring Security Integration**
   - **JWT Authentication**: Secured the API endpoints using JSON Web Tokens (JWT) for authentication and authorization.
   - **Custom Login and Sign-Up Flow**: Implemented custom login and sign-up methods with user details stored in the database.
   - **Username Encryption & Password Hashing**:
     - **Username**: Encrypted before saving in the database to ensure security.
     - **Password**: Hashed using a secure algorithm (e.g., bcrypt) to protect user passwords.

### 2. **Mapping and DTOs**
   - **MapStruct**: Used for mapping between DTOs (Data Transfer Objects) and entities, making the data flow between layers more efficient and reducing boilerplate code.
   
### 3. **Database Layer**
   - **JPA with Hibernate**: Switched from JDBC to JPA for a more streamlined data management approach.
   - **H2 Database**: Used an in-memory H2 database for development and testing purposes.
   - **Dynamic Book Categories**: Added a `Category` entity to organize books by categories such as fiction, non-fiction, science, etc.

### 4. **API Enhancements**
   - **Goodreads Integration**: Future plans include integrating with the Goodreads API to fetch book recommendations based on user preferences and filling the database with book data from Goodreads.
   
### 5. **Advanced Exception Handling**
   - **Global Exception Handling**: Replaced multiple `try-catch` blocks with a global exception handling mechanism using `@ControllerAdvice` and `@ExceptionHandler` annotations for cleaner and more maintainable code.

### 6. **Security Enhancements (Future Plans)**
   - **CAPTCHA**: Implementing CAPTCHA to prevent automated sign-up or login attempts and reduce the risk of brute force attacks.
   - **Rate Limiting**: Planning to add request rate limiting to protect against brute force attacks on login forms by implementing a filter.
   - **Input Validation Filters**: Planning to introduce validation filters to verify and sanitize input data before processing it to enhance security and maintain data integrity.

### 7. **Testing**
   - Writing unit and integration tests to ensure the robustness of the system, particularly for the security components and business logic.

## How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/ebiram400/library-management.git
   ```
   
2. Navigate to the project directory:
   ```bash
   cd library-management
   ```

3. Switch to the `main` branch, which contains the JPA and security implementation:
   ```bash
   git checkout main
   ```

4. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

5. Access the API at:
   - `http://localhost:8080` (for development)

## Future Plans

- **Data Validation**: Implement input data validation through custom filters to enhance security.
- **CAPTCHA**: Prevent brute force attacks with CAPTCHA integration on login forms.
- **Rate Limiting**: Add filters to limit the number of requests to certain endpoints to protect against brute force attacks.
- **Goodreads API Integration**: Fetch book suggestions from Goodreads and populate the database with real-world book data.
- **Category System**: Allow books to be categorized and easily searched by category.
- **Exception Handling Improvements**: Continue improving exception handling to ensure all errors are managed appropriately.

## Project Structure

```
library-management/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com.example.librarymanagement/
│   │   │   │   ├── config/         # Security and app configuration files
│   │   │   │   ├── controller/     # REST Controllers for managing APIs
│   │   │   │   ├── dto/            # Data Transfer Objects for user and book entities
│   │   │   │   ├── entity/         # JPA Entities representing tables in the database
│   │   │   │   ├── exception/      # Custom exceptions and exception handlers
│   │   │   │   ├── repository/     # Repositories for JPA interactions with the database
│   │   │   │   ├── security/       # JWT and security filters, configurations
│   │   │   │   └── service/        # Business logic and services for handling requests
│   │   └── resources/              # Application properties, static resources, etc.
│   └── test/                       # Unit and integration tests
└── README.md                        # Project documentation
```

## License

This project is open-source and available under the [MIT License](LICENSE).
