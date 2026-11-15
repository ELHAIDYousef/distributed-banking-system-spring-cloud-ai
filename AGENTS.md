# AGENTS.md - eBank (distributed-banking-system-spring-cloud-ai)

## Project
Distributed banking system with Spring Boot microservices and Spring Cloud.
Planned parts: customer-service, ebank-service, gateway, discovery,
OpenFeign + Resilience4J between services, a Spring AI agent (OpenAI) with MCP,
Keycloak security, Angular frontend.
School project: the goal is learning how microservices work.
Only trust what exists in the repo. Never create a service that was not asked for.

## Stack
- JDK 21, Spring Boot 4.1.1, Maven
- PostgreSQL in Docker (docker-compose) for development. No Flyway.
- Each service is an independent Maven project (no parent pom).

## Build and test
- Build one service: mvn -q -f <service>/pom.xml package
- Test one service:  mvn -q -f <service>/pom.xml test
- Start the database: docker compose up -d

## Code rules
- Packages are singular: entity, repository, dto, mapper, service, web.
- Business logic goes in the service layer, never in controllers.
- Every new Java class starts with this header:
    /**
     *
     * @author Yousef ELHAID
     */
- No other comments in code.

## Git rules
- Work on a feature branch, never on main.
- Small commits: a header line + a body.
- Never commit .env. Add new variables to .env.example.

## Workflow for agents
- For features, use OpenSpec: propose, wait for my approval, then apply.
- After finishing a feature, use the tester subagent, then the reviewer subagent.
- If something is unclear, ask me instead of guessing.
