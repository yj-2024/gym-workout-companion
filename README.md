# Gym Workout Companion

## Project Overview
Gym Workout Companion is a Spring Boot-based application designed to help gym members manage workout classes, book sessions, track equipment usage, and handle user accounts. It is an intuitive platform for gym-goers to stay connected and organized.

## Technical Features
- **Spring Boot** for backend logic and services.
- **MySQL** for database management.
- **Spring Security** for authentication and authorization.
- **JWT Token Authentication** for secure API requests.
- **Thymeleaf** for rendering HTML views.
- **RESTful APIs** for class and equipment management.

## Installation Instructions
To set up this project on your local machine, follow these steps:

1. **Clone the repository**:
    ```bash
    git clone https://github.com/yourusername/gym-workout-companion.git
    ```

2. **Navigate to the project directory**:
    ```bash
    cd gym-workout-companion
    ```

3. **Set up the database**:
    - Ensure that you have MySQL installed and running.
    - Create a new database `gymworkoutcompanion_db` in MySQL.

4. **Configure the application.properties file**:
    - Open `src/main/resources/application.properties` and configure the database connection settings.
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/gymworkoutcompanion_db
    spring.datasource.username=root
    spring.datasource.password=root
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
    ```

5. **Build the project**:
    - Use Maven to build the project:
    ```bash
    mvn clean install
    ```

6. **Run the application**:
    - After building, run the Spring Boot application using:
    ```bash
    mvn spring-boot:run
    ```

## How to Run the Application
Once the application is running, you can access the frontend at:
http://localhost:8080

bash

- Sign up, log in, and start using the Gym Workout Companion platform to book classes, track your gym sessions, and more.

## Testing
Unit tests for the repository and service layers can be found in the `src/test/java/com/capstone/gymworkoutcompanion` directory. To run the tests, execute the following Maven command:
```bash
mvn test