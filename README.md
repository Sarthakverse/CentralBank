<!-- README: SarthakVerse CentralBank Microservices Platform -->

<div align="center">
  <h1>🏦CentralBank Microservices Platform</h1>
  <p>
    <strong>Enterprise-Grade Distributed Banking Architecture</strong><br>
    Built using Spring Boot • Spring Cloud • Eureka • Gateway • Docker • Observability Stack
  </p>
  <hr width="60%">
</div>

---

## ✨ Overview

**CentralBank** is a production-style, cloud-native distributed banking platform built using Spring Boot and Spring Cloud.

The system simulates a real-world banking ecosystem using:

- Event-Driven Architecture (Kafka)
- API Gateway Pattern
- Service Discovery (Eureka)
- Centralized Configuration (Config Server)
- OAuth2 Security (Keycloak + JWT)
- Distributed Tracing (OpenTelemetry + Tempo)
- Metrics Monitoring (Prometheus)
- Centralized Logging (Loki)
- Dockerized Multi-Service Deployment

This project demonstrates enterprise-grade system design principles, not just CRUD microservices.

---

## 🏗System Architecture
<img width="1761" height="863" alt="Screenshot 2026-02-16 214022" src="https://github.com/user-attachments/assets/fa526bc4-8643-4f69-bcf4-572f95957c37" />
This platform follows a layered cloud-native architecture:

The system is organized into layered architectural components:

### 🔹 Client Layer
- External clients interact only with the API Gateway.

### 🔹 Platform Layer
- Config Server
- Eureka Server
- Kafka
- RabbitMQ

### 🔹 Business Services Layer
- Accounts Service
- Cards Service
- Loans Service

### 🔹 Observability Layer
- Prometheus
- Loki
- Tempo
- Grafana
- Alloy

---

## 🧠 C4 Container Architecture

<img width="1385" height="555" alt="image" src="https://github.com/user-attachments/assets/75c52ff3-fcda-446f-a003-d85029dcc3ac" />


This diagram illustrates system boundaries, container responsibilities, and inter-service relationships.

---

## ⚙ Microservices Overview

| Service | Port | Responsibility |
|----------|------|---------------|
| Config Server | 8071 | Centralized configuration management |
| Eureka Server | 8072 | Service registry & discovery |
| Gateway Server | 8081 | API routing, security enforcement |
| Accounts Service | 8080 | Customer & Account domain |
| Loans Service | 8090 | Loan domain |
| Cards Service | 9000 | Card domain |
| Kafka | 9092 | Event streaming |
| Keycloak | 8180 | OAuth2 Identity Provider |

---

## 🏦 Accounts Service (Core Domain)

<img width="1403" height="442" alt="image" src="https://github.com/user-attachments/assets/daac1232-2d27-4ef2-b447-8c972216e9d8" />


Handles:

- Customer creation
- Account creation
- Publishing `AccountCreateEvent`
- Composite customer details aggregation
- Feign calls to Loans & Cards
- DTO mapping
- Exception handling
- Transaction management
- Audit support

Implements:
- AccountsController
- CustomerController
- AccountsServiceImpl
- KafkaTemplate publisher
- Feign clients

Pattern Used:
Layered Architecture + Event Publisher + API Composition

---

## 💳 Cards Service

Handles:

- Card creation (event-driven)
- Fetch / Update / Delete operations
- Kafka consumer (`account-created-topic`)
- Domain validation
- Independent persistence

Implements:
- CardsController
- CardsServiceImpl
- AccountEventListener

---

## 💰 Loans Service

Handles:

- Loan creation (event-driven)
- Fetch / Update / Delete operations
- Kafka consumer (`account-created-topic`)
- Domain validation
- Independent persistence

Implements:
- LoansController
- LoansServiceImpl
- AccountEventListener

---

## 🔁 Event-Driven Architecture

<img width="1511" height="578" alt="image" src="https://github.com/user-attachments/assets/0c468e65-d1f3-4a75-978d-b699bf6cc732" />


When a new account is created:

1. Accounts Service saves Customer + Account.
2. Publishes `AccountCreateEvent`.
3. Kafka distributes event to:
   - Cards Service (creates credit card)
   - Loans Service (creates home loan)

Kafka consumer groups:
- `cards-group`
- `loans-group`

Pattern:
Event-Driven Microservices (Fan-out)

---

## 🌐 Synchronous Aggregation Flow
<img width="1096" height="655" alt="image" src="https://github.com/user-attachments/assets/f97e5598-9002-4a3a-8d05-ac6383df8e69" />



Accounts Service acts as an API Composition layer.

Flow:
Client → Gateway → Accounts → (Feign) → Cards & Loans → Aggregated Response

Patterns Used:
- API Composition
- Client-side Load Balancing
- Service Discovery via Eureka

---

## 🔐 Security Architecture
<img width="695" height="570" alt="image" src="https://github.com/user-attachments/assets/77d05c07-7fcc-4d0a-ac6c-bf648b2043b6" />


Security Model:

- OAuth2 Authorization Server → Keycloak
- Gateway acts as Resource Server
- JWT validation via issuer-uri
- Stateless authentication
- Zero-trust internal communication

Only Gateway is exposed externally.

---

## 📊 Observability Architecture

<img width="1069" height="490" alt="image" src="https://github.com/user-attachments/assets/01cf7984-03c5-4ab4-ad15-a39e49f8fe26" />


Implements the Three Pillars of Observability:

### Metrics
Micrometer → Prometheus → Grafana

### Logs
Application Logs → Alloy → Loki → Grafana

### Traces
OpenTelemetry → Tempo → Grafana

Provides:
- Distributed tracing
- Performance monitoring
- Centralized log aggregation
- Production diagnostics capability

---

## 🐳 Docker Deployment Architecture

<img width="980" height="648" alt="image" src="https://github.com/user-attachments/assets/f7cbeac2-7f50-426c-bf40-60c42ee4fa72" />


Deployment includes:

- Containerized microservices
- Central bridge network
- Health checks & dependency sequencing
- Kafka cluster (Zookeeper + Broker)
- Observability stack
- Profile-based environment segregation (default / qa / prod)

---

## 🚀 Running the System (Docker)

Navigate to: docker-compose/default
Start services: docker compose up -d


Startup Order:

1. RabbitMQ  
2. Config Server  
3. Eureka Server  
4. Kafka + Zookeeper  
5. Business Services  
6. Gateway  
7. Observability Stack  

---

## 🔎 Access Points

| Component | URL |
|-----------|------|
| Gateway | http://localhost:8081 |
| Eureka Dashboard | http://localhost:8072 |
| Config Server | http://localhost:8071 |
| Grafana | http://localhost:3000 |
| Keycloak | http://localhost:8180 |

---

## 📂 Project Structure

sarthakverse-centralbank/
├── Accounts/
├── Cards/
├── Loan/
├── configServer/
├── eurekaServer/
├── gatewayserver/
└── docker-compose/


Each microservice follows clean layered architecture:

- controller  
- service  
- repository  
- dto  
- entity  
- mapper  
- exception  
- config  
- audit  

---

## 🧠 Architectural Patterns Implemented

- Microservices Architecture  
- Event-Driven Architecture  
- API Gateway Pattern  
- Service Registry Pattern  
- Database per Service Pattern  
- Centralized Configuration Pattern  
- OAuth2 Security Pattern  
- Distributed Tracing Pattern  
- Observability Pattern  
- Containerized Deployment Strategy  

---

## 🎯 Production-Grade Capabilities

✔ Stateless services  
✔ Independent scaling  
✔ Fault isolation  
✔ Event-based decoupling  
✔ Full telemetry pipeline  
✔ Health probes  
✔ Profile-based configuration  
✔ Dockerized deployment  

---

## 👨‍💻 Author

**Sarthak Rastogi**

Designed and engineered as a production-style distributed banking architecture to demonstrate advanced system design, cloud-native architecture, and enterprise microservices patterns.

---

<div align="center">
  <strong>Engineered with precision. Designed for scale. Built for production.</strong>
</div>
