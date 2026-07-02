# BookMyShow Backend

A scalable and production-ready **movie ticket booking backend** built with **Spring Boot**, following RESTful API principles and layered architecture. The application provides a complete backend solution for managing users, movies, theatres, shows, seat reservations, and ticket bookings while demonstrating enterprise Java development practices.

---

## Overview

The project simulates the backend of an online movie ticket booking platform similar to **BookMyShow**. It exposes secure and efficient REST APIs for handling the complete booking workflow, from user registration to ticket confirmation, while ensuring clean architecture, maintainable code, and efficient database management. :contentReference[oaicite:0]{index=0}

---

## Tech Stack

### Backend

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- Maven

### Database

- MySQL

### APIs

- RESTful APIs

---

## Features

- User Management
- Movie Management
- Theatre Management
- Show Scheduling
- Seat Booking System
- Ticket Generation
- RESTful API Architecture
- Layered Architecture
- Database Integration using JPA & Hibernate
- Exception Handling
- Scalable Backend Design

---

## Project Structure

```text
Book_My_show_Backend/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   ├── repository/
│   │   │   ├── entity/
│   │   │   ├── dto/
│   │   │   └── exception/
│   │   └── resources/
│   │       ├── application.properties
│   │       └── ...
│
├── pom.xml
└── README.md
```

---

## Booking Workflow

1. Register and manage users.
2. Add movies and theatres.
3. Schedule movie shows.
4. Display available seats.
5. Book seats for a selected show.
6. Generate booking confirmation and ticket details.

---

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/guru340/Book_My_show-_Backend.git

cd Book_My_show-_Backend
```

---

### Configure Database

Update the MySQL configuration inside:

```properties
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bookmyshow
spring.datasource.username=root
spring.datasource.password=your_password
```

---

### Run the Application

```bash
mvn spring-boot:run
```

The application will start on:

```
http://localhost:8080
```

---

## Build

```bash
mvn clean install
```

---

## Run Tests

```bash
mvn test
```

---

## Learning Concepts

This project demonstrates:

- Spring Boot Fundamentals
- REST API Development
- Layered Architecture
- Spring Data JPA
- Hibernate ORM
- Entity Relationships
- DTO Pattern
- Dependency Injection
- Exception Handling
- Database Design

---

## Future Enhancements

- JWT Authentication & Authorization
- Spring Security
- Online Payment Integration
- Email Ticket Confirmation
- Seat Locking Mechanism
- Admin Dashboard
- Movie Reviews & Ratings
- Docker Support
- CI/CD Pipeline
- Cloud Deployment

---

## Author

**Mayank Sangwani**

- GitHub: https://github.com/guru340

---

If you found this project helpful, consider giving it a ⭐ on GitHub.
