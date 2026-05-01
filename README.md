# 📄 DocumentFlow — Automated Document Approval Workflow API

DocumentFlow is a backend workflow automation system built to manage document submission and approval processes inside an organization.

The application allows authenticated users to upload documents, automatically notifies approvers via email using Spring Integration, receives approval or rejection responses through secure REST APIs, and updates document statuses while maintaining full audit history.

---

## 🚀 Features

* Secure document submission API
* Automated approver email notifications
* Approval / rejection REST endpoints
* Asynchronous workflow management with Spring Integration
* Document status tracking (`PENDING_APPROVAL`, `APPROVED`, `REJECTED`, `ARCHIVED`)
* Audit logging for workflow traceability
* Retry and error handling mechanisms
* JWT Authentication & Role-based Security
* Dockerized deployment
* Swagger/OpenAPI documentation

---

## 🛠 Technologies Used

* Spring Boot
* Spring Integration
* Spring Security + JWT
* Spring Data JPA + Hibernate
* MySQL / PostgreSQL
* Spring Integration Mail
* Docker & Docker Compose
* Swagger/OpenAPI
* Lombok
* Maven

---

## ⚙️ Workflow Overview

1. User submits a document
2. System stores document with `PENDING_APPROVAL` status
3. Spring Integration sends email notification to approver
4. Approver approves or rejects via REST API
5. Document status updates automatically
6. Audit logs record every workflow event

---

## 🔐 Security

* JWT Token Authentication
* BCrypt Password Encryption
* Stateless Session Policy
* Role-Based Authorization

---

## 📖 Swagger Documentation

`http://localhost:8080/swagger-ui/index.html`

---

## 🐳 Run with Docker

```bash id="efq1kx"
docker-compose up --build
```

---
