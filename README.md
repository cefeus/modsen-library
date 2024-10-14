# MODSEN Library  API 

 Library microservices web-api consists of 5 microservices:

- Library Service - main service for all books, authors and genres related operations
- Library Auth Service - service for authorization and authentication, retrieves JWT token
- Library Tracking Service - service for book reservations tracking
- Library Gateway Service -  gateway server, processes all requests
- Library Discovery Service - registerse all microservices

## Stack

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- Spring Web
- Liquibase
- Mapstruct
- PostgresSQL
- Lombok
- JWT
- Spring Cloud
- Spring Cloud Eureka Discovery Server
- Spring Cloud API Gateway

## Requirements

Docker needed.

## How to run

1. Clone repository.
2. Run:
   ```docker-compose up -d```
   or manually run docker-compose.yaml file.
3. Wait few minutes for all services correct deployment.
4. Get Jwt token on auth/sign-in endpoint (auth/sign-up before needed to create user)
5. Use generated JWT to make requests

## POSTMAN 

In postman folder can be found postman collection for API testing. Bearer token update in collection authorization tab needed before use. 
