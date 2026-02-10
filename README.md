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
This document provides a complete and formal description of the CentralBank
microservices system. It covers system architecture, directory structure,
service responsibilities, configuration strategy, environment behavior,
database lifecycle management, and runtime execution.
</p>

<!-- ===================================================== -->

<h2>1. Project Overview</h2>

<p>
CentralBank is a Spring Boot–based microservices platform designed to model
core banking domains using independent, isolated services. Each microservice
is built, configured, and deployed independently.
</p>

<p>
The project emphasizes production-safe practices such as environment-based
configuration, strict database separation, and controlled schema management.
</p>

<!-- ===================================================== -->

<h2>2. Architectural Style</h2>

<p>
The system follows a <strong>microservices architecture</strong>.
Each service is a standalone Spring Boot application with:
</p>

<ul>
    <li>its own codebase</li>
    <li>its own configuration files</li>
    <li>its own database per environment</li>
    <li>its own runtime port</li>
</ul>

<p>
There is no shared database and no shared domain model between services.
</p>

<!-- ===================================================== -->

<h2>3. Complete Directory Structure</h2>

<pre>
CentralBank/
├── Accounts/
│   ├── .mvn/
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/
│       │   │   └── org/sarthak/accounts/
│       │   │       ├── AccountsApplication.java
│       │   │       ├── controller/
│       │   │       ├── service/
│       │   │       ├── repository/
│       │   │       ├── entity/
│       │   │       ├── dto/
│       │   │       └── constants/
│       │   └── resources/
│       │       ├── application.yml
│       │       ├── application-qa.yml
│       │       └── application-prod.yml
│       └── test/
│
├── Cards/
│   ├── .mvn/
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/
│       │   │   └── org/sarthak/cards/
│       │   │       ├── CardsApplication.java
│       │   │       ├── controller/
│       │   │       ├── service/
│       │   │       ├── repository/
│       │   │       ├── entity/
│       │   │       └── dto/
│       │   └── resources/
│       │       ├── application.yml
│       │       ├── application-qa.yml
│       │       └── application-prod.yml
│       └── test/
│
├── Loans/
│   ├── .mvn/
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/
│       │   │   └── org/sarthak/loans/
│       │   │       ├── LoansApplication.java
│       │   │       ├── controller/
│       │   │       ├── service/
│       │   │       ├── repository/
│       │   │       ├── entity/
│       │   │       └── dto/
│       │   └── resources/
│       │       ├── application.yml
│       │       ├── application-qa.yml
│       │       └── application-prod.yml
│       └── test/
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
    <li>Domain: Account management</li>
</ul>

<h3>4.2 Cards Service</h3>
<ul>
    <li>Port: 9000</li>
    <li>Configuration prefix: <code>cards</code></li>
    <li>Domain: Card management</li>
</ul>

<h3>4.3 Loans Service</h3>
<ul>
    <li>Port: 8090</li>
    <li>Configuration prefix: <code>loans</code></li>
    <li>Domain: Loan management</li>
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
    <li><strong>Controller</strong>: REST endpoints</li>
    <li><strong>Service</strong>: Business logic</li>
    <li><strong>Repository</strong>: JPA data access</li>
    <li><strong>Entity</strong>: Persistence models</li>
    <li><strong>DTO</strong>: API request/response contracts</li>
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
    <li>Hibernate schema creation is disabled in Production.</li>
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
CentralBank demonstrates a clean, production-aware microservices architecture
with strict environment separation, independent services, and controlled
database behavior suitable for enterprise-grade systems.
</p>

</body>
</html>
