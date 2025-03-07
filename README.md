# Task tracker project as homework for T1 Java Academy

## Task 1:

Consists of CRUD operations and LoggingAspect with Spring AOP:

- Around advice for benchmarking TaskRepository methods

- Before advice for logging execution of methods annotated with LogBefore

- AfterThrowing advice for logging exception in methods annotated with LogException

- AfterReturning advice for logging completion and result of methods annotated with LogReturning

## Task 2:

- Sending message to Kafka when the task status changes

- Sending notifications through Kafka Consumer with implementation via email

- Using ConfigurationProperties to set Kafka and email parameters

- Support for sending to multiple email addresses asynchronously

## Setup

```bash
docker compose up -d
```

- Application available on the port 8081 
- Postgres available on 5433 (for debugging)
- Kafka available 0n 9092