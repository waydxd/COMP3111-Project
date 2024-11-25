# COMP3111-Project

## Team Information

| Team ID | Member           | Email ID | GitHub ID | Dev Branch ID | Task  |
|---------|------------------|----------|-----------|---------------|-------|
| 9       | CHAN, Wing Yu    | wychancf | waydxd    | Task3         | Task3 |
| 9       | IU, Hei Ching    | hciu     | yxqxq     | Task1         | Task1 |
| 9       | YE, Weicheng     | wyeam    | EZEROR    | Task2         | Task2 |

## Project Overview

This project is a comprehensive exam management system developed as part of the COMP3111 course. The system allows for the management of exams, grades, and user accounts, including teachers and students. The project utilizes various technologies and frameworks to achieve its functionality.

## Technologies and Frameworks Used

### Java
The primary programming language used for the development of the project. Java is used for implementing the core logic, services, and controllers of the application.

### Maven
Maven is used as the build automation tool for the project. It manages project dependencies, builds the project, and runs tests.

### SQLite
SQLite is used as the database for storing all the data related to exams, grades, and user accounts. It is a lightweight, file-based database that is easy to set up and use.

### JOOQ
JOOQ (Java Object Oriented Querying) is used as the database access library. It provides a fluent API for typesafe SQL query construction and execution. JOOQ helps in generating SQL queries and mapping the results to Java objects.

### JavaFX
JavaFX is used for building the graphical user interface (GUI) of the application. It provides a rich set of UI controls and features for developing desktop applications.

### JUnit
JUnit is used for writing and running unit tests. It helps in ensuring the correctness of the code by providing a framework for writing repeatable tests.

### Mockito
Mockito is used for mocking dependencies in unit tests. It allows for the creation of mock objects and the definition of their behavior, which helps in isolating the code under test.

## Project Structure

The project is organized into the following main packages:

- `comp3111.examsystem.controller`: Contains the controllers for handling user interactions and managing the flow of the application.
- `comp3111.examsystem.dao`: Contains the Data Access Object (DAO) classes for interacting with the database.
- `comp3111.examsystem.entity`: Contains the entity classes representing the data models.
- `comp3111.examsystem.service`: Contains the service classes that implement the business logic of the application.
- `comp3111.examsystem.util`: Contains utility classes and helper methods.

## How to Run the Project

1. **Clone the repository**:
   ```sh
   git clone https://github.com/waydxd/COMP3111-Project.git
   cd COMP3111-Project
   ```

2. **Build the project using Maven**:
   ```sh
   mvn clean install
   ```

3. **Run the application**:
   ```sh
   mvn javafx:run
   ```

## How to Run Tests

To run the tests, use the following Maven command:
```sh
mvn test
```

## Contributing

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Make your changes and commit them (`git commit -am 'Add new feature'`).
4. Push to the branch (`git push origin feature-branch`).
5. Create a new Pull Request.

## License

This project is not licensed.
