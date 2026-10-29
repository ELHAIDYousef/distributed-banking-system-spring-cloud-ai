# Distributed Banking System — Spring Cloud & Spring AI

A microservices banking application built step by step, following Prof. Mohamed YOUSSFI's course series, with **PostgreSQL in Docker** instead of H2.

## Architecture

```mermaid
flowchart TB
    client[Client: Browser / Postman / Swagger]
    chat[Chat clients: REST / Telegram / Discord]
    gateway[gateway-service :8888]
    discovery[discovery-service :8761<br/>Eureka]
    customer[customer-service :8081<br/>+ MCP server]
    account[account-service :8082<br/>+ MCP server]
    bot[ebank-bot :8084<br/>Spring AI agent + MCP client]
    llm[LLM provider<br/>Groq / OpenAI]
    cdb[(customer-db<br/>PostgreSQL :5433)]
    adb[(account-db<br/>PostgreSQL :5434)]

    client --> gateway
    gateway -- lb:// --> customer
    gateway -- lb:// --> account
    account -- OpenFeign + Resilience4J --> customer
    chat --> bot
    bot <--> llm
    bot -- MCP --> customer
    bot -- MCP --> account
    customer --> cdb
    account --> adb
    customer -. register .-> discovery
    account -. register .-> discovery
    gateway -. register / lookup .-> discovery
    bot -. register .-> discovery
```

## Services

| Service | Port | Role |
|---|---|---|
| `discovery-service` | 8761 | Eureka service registry |
| `gateway-service` | 8888 | Single entry point, routes requests to services |
| `customer-service` | 8081 | Customers (Spring Data REST) + MCP server |
| `account-service` | 8082 | Bank accounts, enriched with customer data via OpenFeign + MCP server |
| `ebank-bot` | 8084 | AI agent (Spring AI + MCP client) for REST, Telegram and Discord |
| `customer-db` | 5433 | PostgreSQL for customer-service |
| `account-db` | 5434 | PostgreSQL for account-service |

## Tech stack

| Area | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 4.1.1, Spring Cloud 2025.1.3 |
| Persistence | Spring Data JPA, Spring Data REST, PostgreSQL 18 |
| Communication | OpenFeign, Spring Cloud LoadBalancer |
| Resilience | Resilience4J (circuit breaker) |
| Discovery / Routing | Eureka, Spring Cloud Gateway (WebFlux) |
| AI | Spring AI 2.0, MCP (Streamable HTTP), Groq (OpenAI-compatible API) |
| Chat channels | TelegramBots 10, JDA 6 (Discord) |
| Docs / Tests | springdoc OpenAPI (Swagger UI), Postman, MCP Inspector |
| Infrastructure | Docker Compose |
| Test data | Datafaker |

## Getting started

### Prerequisites

- JDK 21
- Docker and Docker Compose
- IntelliJ IDEA (or any IDE) — Maven wrapper included in each module

### 1. Environment variables

Copy `.env.example` to `.env` at the project root, then set real values: database passwords, `GROQ_API_KEY`, `TELEGRAM_BOT_TOKEN`, `DISCORD_BOT_TOKEN`. `.env` is ignored by Git.

### 2. Start the databases

Start them with Docker Compose from the project root, and wait until both containers are `healthy`.

### 3. Start the services (in this order)

1. `discovery-service`
2. `customer-service`
3. `account-service`
4. `gateway-service`
5. `ebank-bot` — last, because it connects to the MCP servers at startup

### 4. Check

| URL | Expected |
|---|---|
| http://localhost:8761 | Eureka dashboard with 4 registered services |
| http://localhost:8888/customers | Customers through the gateway |
| http://localhost:8888/accounts | Accounts with their customer |
| http://localhost:8888/customer-service/customers | Same, using the service name |
| http://localhost:8081/swagger-ui.html | Customer API documentation |
| http://localhost:8082/swagger-ui.html | Account API documentation |
| http://localhost:8084/chat?query=List%20the%20customers | AI agent answer using the MCP tools |

A Postman collection is available in [`postman/`](postman/ebank.postman_collection.json).

## Project parts

| # | Branch | Content | Doc |
|---|---|---|---|
| 1 | `feature/core-services` | Customer & Account services, OpenFeign, Resilience4J, Eureka, Gateway, Swagger, Postman | [docs/01-core-services.md](docs/01-core-services.md) |
| 2 | `feature/ai-agent` | MCP servers, Spring AI agent with memory, REST / Telegram / Discord bots | [docs/02-ai-agent.md](docs/02-ai-agent.md) |
| 3 | _next_ | Angular frontend | _coming next_ |

## Commit convention

[Conventional Commits](https://www.conventionalcommits.org): `type(scope): summary`, with a body explaining what and why.

Types used: `feat`, `fix`, `refactor`, `chore`, `build`, `docs`.

## Author

**Yousef ELHAID** — Software Engineering student (GLSID), ENSET Mohammedia
