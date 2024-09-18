A simple library management system built with Java 17 and Spring Boot 2.7.18. This project provides basic functionality to manage a library, including features to view, add, and delete books, as well as role management (Manager, Librarian, and Member).

## Features

- View a list of available books
- Add new books
- Delete books
- User role management (Manager, Librarian, Member)
- Custom login system (no Spring Security)

## Technologies Used

- Java 17
- Spring Boot 2.7.18
- JDBC for database operations
- Thymeleaf for template rendering
- Swagger UI for API documentation
- SLF4J for logging

## Project Structure

library-management/ ├── src/ ├── main/ ||| ├── java/      ||| ├── ir.dotin.softwaresystems.librarymanagement/ ||| ├──controller/
                             └── test/     └── resources/                                                         └── dto/
                                                                                                                  └── exceptions/
                                                                                                                  └── repository/
                                                                                                                  └── service/
## Setup Instructions

1. Clone the repository:

   
bash
    git clone https://github.com/ebiram400/library-management.git
    

2. Navigate to the project directory:

   
bash
    cd library-management
    

3. Build and run the project:

    You can build the project using Maven:

   
bash
    mvn clean install
    

    Then run the Spring Boot application:

   
bash
    mvn spring-boot:run
    

4. Access the application:

    After starting the application, visit [http://localhost:8080](http://localhost:8080) in your browser.

## API Endpoints

Here are the available API endpoints for managing books:

- GET /books - Retrieve a list of books
- POST /books - Add a new book
- DELETE /books/{id} - Delete a book by ID

## Logging

Logging is implemented using SLF4J. Logs are placed throughout the service layer to track important operations.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
