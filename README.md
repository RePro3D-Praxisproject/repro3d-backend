# Repro3D Platform Project (Backend)

## PRAXIS PROJECT 2024-2025 FH Burgenland

## Badges
![MariaDB](https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
## Overview

Repro3D (Backend) is a microservices-based project designed to provide REST API endpoints for the frontend (separate repository).

The system is composed of several Spring modules, including an API Gateway, Authentication Service, Discovery Server, Billing Service, Order Service, and Printer Service.
Each module handles specific functionality and communicates with the frontend via HTTP requests from said frontend.

## Architecture

The project is structured as follows:

- **API Gateway**: Manages routing and handles authentication (routes it where needed).
- **Authentication Service**: Manages user authentication and authorization.
- **Discovery Server**: Provides service discovery for dynamic configuration.
- **Billing Service**: Manages billing and redeem code operations.
- **Order Service**: Handles orders and related operations.
- **Printer Service**: Manages printer operations and status.

## Prerequisites

- Java 21
- Apache Maven 3.9.5
- MariaDB 15.1 (used for persistence storage)

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/RePro3D-Praxisproject/repro3d-backend.git
cd RePro3D-Praxisproject/repro3d-backend
```

## Setting Up the Environment


Ensure you have the required configuration set up for database connections and correct discovery to work. 
The configuration for each module is available via their corresponding `application.properties` files.

Example `application-properties`:

```
spring.application.name=service name
server.port=port number
spring.datasource.url=jdbc:mariadb://x:3306/GeneralDb
spring.datasource.username=x
spring.datasource.password=x
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MariaDBDialect
#spring.jpa.hibernate.ddl-auto=update
#logging.level.root=debug


# Eureka Client Configuration
eureka.client.serviceUrl.defaultZone=http://localhost:x/eureka/
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true
```

As for the database, MariaDB instance has to be running and instead of `x` in `spring.datasource.url` should be proper IP-Address of said MariaDB instance.

In the `spring.datasource.username` and `spring.datasource.password`
correct credentials have to be present.

Instead of `GeneralDb` any DB schema can be put, but it has to be the same for every module and/or created beforehand in the MariaDB instace.


## Build the Project
Use Maven to build the project:
```
mvn clean install
```

## Running the Services

All services can be launched via single command:
```
mvn spring-boot:run
```

In case if services have to be started individually, 
then this can be done as following:

1. Start the Discovery Server

```
cd DiscoveryServer
mvn spring-boot:run
```

2. Start Api gateway
```
cd API-Gateway
mvn spring-boot:run
```

3. Start the Authentication Service
```
cd AuthService
mvn spring-boot:run
```
4. Start the Billing Service
```
cd billing-service
mvn spring-boot:run
```

5. Start the Order Service
```
cd OrderService
mvn spring-boot:run
```

6. Start the Printer Service
```

cd printer-service
mvn spring-boot:run
```

## Services Overview

### API Gateway
The API Gateway handles routing and authentication. It forwards requests to the appropriate service based on the request path.
### Authentication Service
This service manages user authentication and authorization. It provides endpoints for user registration, login, and token validation.
### Discovery Server
The Discovery Server uses Eureka for service discovery, allowing microservices to find and communicate with each other dynamically.
### Billing Service
Manages billing operations and redeem code functionalities. It includes endpoints to create, update, validate, and delete redeem codes.
### Order Service
Handles order creation, update, and retrieval operations. It maintains the state and information related to orders.
### Printer Service
Manages 3D printer operations, including starting print jobs and checking printer status.

## API Endpoints

For interacting with API Endpoints OpenAPI specification may be found under `OpenAPI` folder. 
That demonstrates all the endpoints and their example responses.

To test endpoints manually `curl` maybe be used.

Replace `api-gateway` with `localhost` as example.
### Examples
```
curl -X GET http://api-gateway:8765/api/role/{id}
curl -X GET http://api-gateway:8765/api/user/id/{id}
curl -X POST http://api-gateway:8765/api/user 
curl -X DELETE http://api-gateway:8765/api/receipt/{id}
curl -X POST http://api-gateway:8765/api/printer 
curl -X GET http://api-gateway:8765/api/printer/{id}/apikey 
curl -X GET http://api-gateway:8765/api/item/{id}
curl -X POST http://api-gateway:8765/api/item

```

## The Team


* Balázs Máhli  https://github.com/mazsibazsi
* Artem Daminov https://github.com/LostPersona
* Hossein Amir https://github.com/Hosseinamir1989





## License
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

This project is licensed under the GNU General Public License [see](LICENSE) the  file for details.
