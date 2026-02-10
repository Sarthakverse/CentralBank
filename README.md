<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>CentralBank – System Architecture Documentation</title>

  <style>
    body {
      font-family: Arial, Helvetica, sans-serif;
      margin: 40px;
      line-height: 1.6;
      color: #000;
    }

    h1, h2, h3, h4 {
      margin-top: 32px;
    }

    h1 {
      font-size: 28px;
    }

    h2 {
      font-size: 22px;
      border-bottom: 1px solid #000;
      padding-bottom: 4px;
    }

    h3 {
      font-size: 18px;
    }

    p {
      max-width: 900px;
    }

    pre {
      background: #f5f5f5;
      padding: 12px;
      border: 1px solid #ccc;
      overflow-x: auto;
      font-size: 13px;
    }

    code {
      font-family: Consolas, monospace;
    }

    table {
      border-collapse: collapse;
      margin-top: 12px;
      width: 900px;
    }

    th, td {
      border: 1px solid #000;
      padding: 8px;
      text-align: left;
    }

    th {
      background: #eaeaea;
    }

    ul {
      margin-left: 20px;
    }
  </style>
</head>

<body>

<h1>CentralBank Microservices Platform</h1>

<p>
This document describes the architecture, structure, configuration strategy,
and runtime behavior of the CentralBank microservices system.
</p>

<!-- ===================================================== -->

<h2>1. Project Overview</h2>

<p>
CentralBank is a Spring Boot–based microservices platform that models
core banking domains using independent services. Each service is isolated
at the code, configuration, and database level.
</p>

<p>
The project demonstrates production-safe backend practices, including
environment-specific configuration, controlled database schema management,
and profile-driven behavior.
</p>

<!-- ===================================================== -->

<h2>2. High-Level Architecture</h2>

<p>
The system follows a <strong>microservices architecture</strong>.
Each service is a standalone Spring Boot application with its own runtime,
configuration, and database.
</p>

<p>
There is no shared code and no shared database between services.
</p>

<!-- ===================================================== -->

<h2>3. Complete Directory Structure</h2>

<pre>
CentralBank/
├── Accounts/
│   ├── pom.xml
│   ├── mvnw / mvnw.cmd
│   └── src/
│       ├── main/
│       │   ├── java/org/sarthak/accounts/
│       │   │   ├── AccountsApplication.java
│       │   │   ├── controller/
│       │   │   ├── service/
│       │   │   ├── repository/
│       │   │   ├── entity/
│       │   │   ├── dto/
│       │   │   └── constants/
│       │   └── resources/
│       │       ├── application.yml
│       │       ├── application-qa.yml
│       │       └── application-prod.yml
│       └── test/
│
├── Cards/
│   ├── pom.xml
│   └── src/
│       ├── main/java/org/sarthak/cards/
│       └── main/resources/
│           ├── application.yml
│           ├── application-qa.yml
│           └── application-prod.yml
│
├── Loans/
│   ├── pom.xml
│   └── src/
│       ├── main/java/org/sarthak/loans/
│       └── main/resources/
│           ├── application.yml
│           ├── application-qa.yml
│           └── application-prod.yml
│
├── docker-compose.yml
└── README.md
</pre>

<!-- ===================================================== -->

<h2>4. Microservices Description</h2>

<h3>4.1 Accounts Service</h3>
<ul>
  <li>Port: 8080</li>
  <li>Configuration prefix: <code>accounts</code></li>
  <li>Responsibility: Account-related domain operations</li>
</ul>

<h3>4.2 Cards Service</h3>
<ul>
  <li>Port: 9000</li>
  <li>Configuration prefix: <code>cards</code></li>
  <li>Responsibility: Card-related domain operations</li>
</ul>

<h3>4.3 Loans Service</h3>
<ul>
  <li>Port: 8090</li>
  <li>Configuration prefix: <code>loans</code></li>
  <li>Responsibility: Loan-related domain operations</li>
</ul>

<!-- ===================================================== -->

<h2>5. Internal Service Architecture</h2>

<p>
Each service follows a layered architecture:
</p>

<pre>
Controller → Service → Repository → Database
</pre>

<ul>
  <li><strong>Controller</strong>: REST API endpoints</li>
  <li><strong>Service</strong>: Business logic</li>
  <li><strong>Repository</strong>: JPA-based data access</li>
  <li><strong>Entity</strong>: Persistence models</li>
  <li><strong>DTO</strong>: API request/response models</li>
</ul>

<!-- ===================================================== -->

<h2>6. Environment and Profile Strategy</h2>

<table>
  <tr>
    <th>Environment</th>
    <th>Profile</th>
    <th>Database</th>
    <th>ddl-auto</th>
    <th>Purpose</th>
  </tr>
  <tr>
    <td>Local</td>
    <td>default</td>
    <td>H2 (in-memory)</td>
    <td>update</td>
    <td>Developer productivity</td>
  </tr>
  <tr>
    <td>QA</td>
    <td>qa</td>
    <td>PostgreSQL</td>
    <td>update</td>
    <td>Integration testing</td>
  </tr>
  <tr>
    <td>Production</td>
    <td>prod</td>
    <td>PostgreSQL</td>
    <td>validate</td>
    <td>Schema safety</td>
  </tr>
</table>

<!-- ===================================================== -->

<h2>7. Database Strategy</h2>

<ul>
  <li>Each service uses its own database per environment.</li>
  <li>H2 is used only in local development.</li>
  <li>PostgreSQL is used in QA and Production.</li>
  <li>Hibernate never creates or modifies schema in production.</li>
</ul>

<!-- ===================================================== -->

<h2>8. Environment Variables</h2>

<p>
The following environment variables are required for QA and Production:
</p>

<pre>
DB_USERNAME
DB_PASSWORD
</pre>

<!-- ===================================================== -->

<h2>9. Running the Services</h2>

<h3>9.1 Local (DEV)</h3>
<pre>
mvn spring-boot:run
</pre>

<h3>9.2 QA</h3>
<pre>
SPRING_PROFILES_ACTIVE=qa
DB_USERNAME=postgres
DB_PASSWORD=****
</pre>

<h3>9.3 Production</h3>
<pre>
SPRING_PROFILES_ACTIVE=prod
DB_USERNAME=postgres
DB_PASSWORD=****
</pre>

<!-- ===================================================== -->

<h2>10. Explicit Non-Goals</h2>

<ul>
  <li>No API Gateway</li>
  <li>No Service Discovery</li>
  <li>No Inter-service communication</li>
  <li>No Authentication or Authorization</li>
</ul>

<!-- ===================================================== -->

<h2>11. Conclusion</h2>

<p>
This project demonstrates a clean, production-aware microservices architecture
with strict environment separation and controlled database behavior.
</p>

</body>
</html>
