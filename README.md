# Aspire Backend Coding Challenge

## Overview
This application is built in Spring Boot v3.3.1 Framework using Java version 21 and Maven as the build tool.
The application exposes Rest APIs for the user to interact with it.
Unit Tests are written in JUnit framwork.

The application is designed as per the layered architecture. 
The Controller layer describes the API endpoints and performs validations.
The Service layer describes the business logic.
The Storage layer is holding the data while the application is running.

PS: Database can be integrated if data needs to be persisted beyond the application's lifecycle.


### How to Run the Application
1. Install JDK 21 (https://www.oracle.com/in/java/technologies/downloads/#java21).

2. Clone this repository in your local system.

3. Run the following command to install the dependencies and build the JAR after running Unit Tests:
```
./mvnw.cmd clean install
```
4. Run the following command to start the web server on 8090 port:
```
./mvnw.cmd spring-boot:run
```
5. Swagger URL as per OpenAPI specs: 
http://localhost:8090/swagger-ui/index.html


### User Management
By Default, 3 users are created when the application is launched.
```
1 . Username - "aspire"
    Password - "aspire"
    Type - ADMIN
```
```
1 . Username - "customer"
    Password - "customer"
    Type - CUSTOMER
```
```
1 . Username - "customer2"
    Password - "customer2"
    Type - CUSTOMER
```
All the user details can be viewed via this API: http://localhost:8090/swagger-ui/index.html#/user-controller/getAllUsers (user/pass: superadmin/superadmin)

Authentication for every API is done via username/password which need to passed in the API headers.

#### SWAGGER UI SHOULD BE USED TO INTERACT WITH THE APPLICATION

### Storage Layer
Currently all the Data is stored in the ArrayList Data structure. This can be further optimised depending upon the scaling requirements.