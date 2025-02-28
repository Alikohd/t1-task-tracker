# Task tracker project as homework for T1 Java Academy

## Task 1:

Consists of CRUD operations and LoggingAspect with Spring AOP:

- Around advice for benchmarking TaskRepository methods

- Before advice for logging execution of methods annotated with LogBefore

- AfterThrowing advice for logging exception in methods annotated with LogException

- AfterReturning advice for logging completion and result of methods annotated with LogReturning

## Setup

```bash
docker compose up -d
```

The application available on the port 8081 and Postgres available on 5433 (for debugging)