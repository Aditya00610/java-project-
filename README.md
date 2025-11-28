# Expense Watch — Backend

A production-like Spring Boot backend for personal expense tracking with JWT authentication.

## What this repo contains
- Spring Boot 3 application (Java 17)
- JWT-based authentication (register/login)
- Expense CRUD scoped to authenticated users
- Reports: totals by category and by month
- CSV export
- Centralized exception handling and logging
- Postman collection to try APIs locally

## How to run
1. Install Java 17, Maven, and MySQL.
2. Update `src/main/resources/application.properties` with your DB credentials and a secure `jwt.secret`.
3. Create the database manually or run `src/main/resources/schema.sql`.
4. (Optional) adjust `data.sql` for initial seed users.
5. Package & run:

```bash
mvn clean package
java -jar target/expense-watch-1.0.0.jar
```

## API (quick)
- `POST /api/auth/register` — register (body: username,password)
- `POST /api/auth/login` — login => returns JWT
- `GET /api/users/me` — current user
- `POST /api/expenses` — create expense (authenticated)
- `GET /api/expenses` — list expenses
- `GET /api/expenses/report/category?start=YYYY-MM-DD&end=YYYY-MM-DD`
- `GET /api/expenses/export/csv?start=YYYY-MM-DD&end=YYYY-MM-DD` — download CSV

## Interview talking points
- Explain layered architecture (Controller-Service-Repository)
- JWT flow: login -> token -> Authorization header
- Data ownership: each expense belongs to a user — enforced in service
- Aggregation queries executed at DB level for performance
- How to extend: pagination, file upload for receipts, scheduled email alerts, metrics/monitoring

## Next steps to show in interviews
- Add Swagger/OpenAPI
- Add integration tests using Testcontainers for MySQL
- CI/CD pipeline with GitHub Actions and Docker image build
write this same in 300 words 
