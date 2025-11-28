Expense Watch — Backend 

Expense Watch is a production-style Spring Boot backend designed for personal expense tracking, featuring secure JWT-based authentication and a clean, scalable architecture. Built using Spring Boot 3 and Java 17, the project demonstrates real-world backend development practices, including layered design, validation, exception handling, logging, and database interactions through JPA.

The application provides a complete authentication module where users can register and log in. After successful login, the system issues a JWT, allowing secure access to protected endpoints. Each expense record is linked to a specific user, ensuring strict data isolation and ownership enforcement at the service layer.

Core functionality includes full CRUD operations for expenses, enabling users to create, update, delete, and view their spending. In addition to basic operations, the project includes reporting features such as category-wise and month-wise summaries. These reports rely on optimized aggregation queries executed at the database level for better performance when handling larger datasets. Users can also export filtered expenses as CSV files.

Running the project is straightforward: set up Java 17, Maven, and MySQL; configure application properties; initialize the database; and run using mvn clean package followed by executing the generated JAR file. A Postman collection is included for quick testing of all APIs.

For interviews, you can highlight concepts such as JWT authentication flow, layered architecture (Controller → Service → Repository), exception handling strategy, and how user-scoped data security is maintained. You can also discuss potential enhancements like pagination, receipt uploads, scheduled reminders, or monitoring dashboards.

Recommended next steps for showcasing the project include adding Swagger/OpenAPI documentation, writing integration tests with Testcontainers, and setting up a CI/CD pipeline using GitHub Actions with Docker image deployment.
