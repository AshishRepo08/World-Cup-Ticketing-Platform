# World Cup Ticketing Platform

Spring Boot microservices-based ticket booking platform for FIFA World Cup matches.

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

1. User places an order.
2. Order Service reserves tickets from Inventory Service.
3. Order is saved in MySQL.
4. Order Service publishes an OrderPlacedEvent to Kafka.
5. Notification Service consumes the event.
6. Email confirmation is sent through Mailpit.

## Ports

| Service | Port |
|---|---|
| API Gateway | 9000 |
| Match Schedule Service | 8080 |
| Inventory Service | 8082 |
| Order Service | 9001 |
| Notification Service | 8083 |
| Mailpit UI | 8025 |
| Kafka UI | 8086 |

## Sample APIs

...

<img width="1840" height="1060" alt="System Diagram" src="https://github.com/AshishRepo08/5---World-Cup-Ticket-Platform/blob/a46f704968a468d09e013c97eaa9f3177b1a87da/Design.png" />
