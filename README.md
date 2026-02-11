<!-- README: SarthakVerse CentralBank Microservices Platform -->

<div align="center">
  <h1>🏦 SarthakVerse — CentralBank Microservices Platform</h1>
  <p>
    <strong>Enterprise-Grade Distributed Banking Architecture</strong><br>
    Built using Spring Boot • Spring Cloud • Eureka • Gateway • Docker
  </p>
  <hr width="60%">
</div>

---

## ✨ Overview

**SarthakVerse CentralBank** is a cloud-native, distributed microservices banking platform built using modern Spring Boot and Spring Cloud technologies.

The system demonstrates production-grade architectural patterns including:

- API Gateway Pattern  
- Service Discovery (Eureka)  
- Centralized Configuration (Config Server)  
- Inter-Service Communication (Feign Clients)  
- Distributed Request Tracing (Correlation ID)  
- Dockerized Multi-Environment Deployment  
- Profile-Based Configuration (default / qa / prod)

This project simulates a simplified banking ecosystem with independent domain-driven services.

---

## 🏗 Architecture Overview
<img width="768" height="828" alt="Screenshot 2026-02-12 011149" src="https://github.com/user-attachments/assets/edffc420-3f4e-4a20-af51-1959a6a148da" />

The system consists of the following microservices:

| Service | Port | Responsibility |
|----------|------|---------------|
| Config Server | 8071 | Centralized configuration management |
| Eureka Server | 8072 | Service registry & discovery |
| Gateway Server | 8081 | API routing, filters, tracing |
| Accounts Service | 8080 | Customer & Account domain |
| Loans Service | 8090 | Loan domain |
| Cards Service | 9000 | Card domain |
| RabbitMQ | 5672 | Messaging infrastructure |

---

## 🧩 Microservices Breakdown
**End-to-End Microservices Request Flow Sequence Diagram**
<img width="1153" height="614" alt="Screenshot 2026-02-12 011248" src="https://github.com/user-attachments/assets/b1e4b905-d33f-4f40-b0a5-7c6ea5f0f3bb" />


### 🏦 Accounts Service
Handles:
- Customer creation
- Account creation
- Fetching customer composite details
- Feign calls to Loans & Cards
- DTO mapping
- Exception handling
- Auditing
- Schema initialization

Implements:
- `CustomerController`
- `AccountsController`
- `CustomerServiceImpl`
- Feign clients for Loans & Cards

---

### 💳 Cards Service
Handles:
- Card creation
- Update
- Delete
- Fetch by mobile number
- Profile-based configuration
- Validation & exception handling

---

### 💰 Loans Service
Handles:
- Loan creation
- Update
- Delete
- Fetch by mobile number
- Contact info configuration
- Audit logging

---

### ⚙ Config Server
**Centralized Configuration Management Architecture Diagram**
<img width="1111" height="362" alt="Screenshot 2026-02-12 011319" src="https://github.com/user-attachments/assets/47cd4ca6-7fbf-45ba-8391-baf7a1606e40" />

Centralized configuration source for:

- accounts.yml
- accounts-qa.yml
- accounts-prod.yml
- loans.yml
- cards.yml
- gatewayserver.yml
- eurekaserver.yml

Supports profile-based segregation.

---

### 🌐 Eureka Server
**Service Registration & Discovery Architecture Diagram**
<img width="1229" height="502" alt="Screenshot 2026-02-12 011306" src="https://github.com/user-attachments/assets/20f36307-3d10-480b-9961-fcb2dd390d42" />

Provides:
- Service registration
- Service discovery
- Load-balanced communication
- Health monitoring

All microservices register themselves here.

---

### 🚪 Gateway Server

Acts as system entry point.

Features:
- Load-balanced routing using `lb://`
- Path rewriting
- Global request filter
- Global response filter
- Correlation ID propagation
- Response header injection
- Dynamic route configuration using `RouteLocatorBuilder`

Example routing:
- `/centralbank/accounts/**`
- `/centralbank/loans/**`
- `/centralbank/cards/**`


Implements:
- RequestTraceFilter
- ResponseTraceFilter
- FilterUtility
- Custom route configuration

---

## 🔁 Inter-Service Communication

Accounts service uses:

- `CardsFeignClient`
- `LoansFeignClient`

Communication flow:

Client → Gateway → Accounts → (Feign) → Loans & Cards

---

## 🔍 Distributed Request Tracing
**Distributed co-relation id diagram**
<img width="1134" height="593" alt="image" src="https://github.com/user-attachments/assets/856ddd2b-6f34-4e80-bd8e-a7e3ade479b0" />

Gateway generates a unique: **centralbank-correlation-id**
Flow:
1. If request already contains correlation ID → reused
2. Else → generated at Gateway
3. Propagated to downstream services
4. Returned in response headers

Ensures traceability across services.

---
## 🐳 Docker Deployment

Project includes multi-environment docker setup:
```
docker-compose/
├── default/
├── qa/
└── prod/
```


Each environment contains:
- docker-compose.yml
- common-config.yml

Supports:
- Profile activation
- Network isolation
- Service dependency sequencing
- Environment variable injection

---

## 🚀 How To Run (Docker)

Navigate to: **docker-compose/default**

Then: **System startup order**
1. RabbitMQ
2. Config Server
3. Eureka Server
4. Accounts / Loans / Cards
5. Gateway

---

## 🔎 Access Points

| Component | URL |
|-----------|------|
| Gateway | http://localhost:8081 |
| Eureka Dashboard | http://localhost:8072 |
| Config Server | http://localhost:8071 |
| Accounts API | http://localhost:8081/centralbank/accounts/... |

---

## 📂 Project Structure
```
sarthakverse-centralbank/
├── Accounts/
├── Cards/
├── Loan/
├── configServer/
├── eurekaServer/
├── gatewayserver/
└── docker-compose/
```



Each service follows clean layered architecture:

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

## 🧠 Architectural Concepts Implemented

- Microservices Architecture
- Service Registry Pattern
- API Gateway Pattern
- Centralized Configuration Pattern
- Circuit-ready design
- DTO Mapping Layer
- Exception Handling Strategy
- Distributed Tracing (Manual)
- Dockerized Environment Strategy
- Environment-based Config Segregation

---

## 🎯 Future Enhancements

Planned roadmap:

- JWT Authentication at Gateway
- Resilience4j Circuit Breaker
- Rate Limiting
- Distributed Tracing with Zipkin
- Centralized Logging (ELK Stack)
- Prometheus & Grafana Monitoring
- Kubernetes Deployment
- CI/CD Integration
- API Documentation Consolidation

---

## 👨‍💻 Author

**Sarthak Rastogi**

Built as an enterprise-grade distributed system learning project to simulate real-world banking microservices architecture.

---

<div align="center">
  <strong>Engineered with precision. Designed for scale.</strong>
</div>


