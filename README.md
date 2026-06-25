# **FIFA World Cup Ticket Platform**

A Spring Boot microservices-based ticket booking platform for FIFA World Cup matches.

## Architecture

- API Gateway
- Match Schedule Service
- Inventory Service
- Order Service
- Notification Service
- Kafka
- MySQL
- MongoDB
- Mailpit

## Flow

1. User places ticket order.
2. Order Service checks ticket availability from Inventory Service.
3. If available, order is saved in MySQL.
4. Order Service publishes OrderPlacedEvent to Kafka.
5. Notification Service consumes event.
6. Confirmation email is sent through Mailpit.

## Tech Stack

Java 21, Spring Boot, Spring Data JPA, MongoDB, MySQL, Kafka, Spring Cloud Gateway, Resilience4j, Docker, Mailpit.

## Run Locally

...
<img width="1840" height="1060" alt="System Diagram" src="https://github.com/AshishRepo08/5---World-Cup-Ticket-Platform/blob/a46f704968a468d09e013c97eaa9f3177b1a87da/Design.png" />
