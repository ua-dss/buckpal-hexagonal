# Hexagonal Architecture Demo
This repository implements a small web application following the Hexagonal Architecture style discussed in the book "Get Your Hands Dirty on Clean Architecture". It updates the author’s original implementation in [Buckpal](https://github.com/thombergs/buckpal).

## Technology Stack

- **Language**: Java 17
- **Framework**: Spring Boot 3.1.0
- **Build Tool**: Gradle
- **Database**: H2 (in-memory for testing, configured for runtime)
- **Persistence**: Spring Data JPA / Hibernate
- **Architecture**: Hexagonal Architecture (Ports & Adapters)
- **Testing**: JUnit 5, Mockito, AssertJ, ArchUnit
- **Code Quality**: Architecture Unit Tests for dependency validation

## Instructions to Deploy

### Prerequisites
- Docker and Docker Compose (for dev container setup)
- VS Code with Remote - Containers extension

### Local Development Setup

1. **Clone the Repository**
   ```bash
   git clone <repository-url>
   cd buckpal-hex
   ```

2. **Open in Dev Container (Recommended)**
   - Open the folder in VS Code
   - Use "Open Folder in Container..." option
   - VS Code will automatically build and launch the dev container with Java 17 and Gradle

3. **Build the Project**
   ```bash
   ./gradlew build
   ```

4. **Run Tests**
   ```bash
   ./gradlew test
   ```

5. **Run the Application**
   ```bash
   ./gradlew bootRun
   ```
	The application starts on `http://localhost:8080`.

	The H2 console is available at `http://localhost:8080/h2-console`. Set `JDBC_URL = jdbc:h2:mem:db` and `Driver class = org.h2.Driver`

6. **Generate Javadoc**
   ```bash
   ./gradlew javadoc
   ```
   Documentation will be generated in the project root directory

### API Endpoints

- `GET /accounts/{id}/balance` - Get account balance
- `POST /accounts/send` - Send money between accounts
- `POST /accounts/deposit` - Deposit money to account
- `POST /accounts/withdraw` - Withdraw money from account
- `POST /accounts/create` - Create new account
- `GET /accounts` - List all accounts

