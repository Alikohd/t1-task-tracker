package com.example.t1tasktracker.integration;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.ConfluentKafkaContainer;

@Testcontainers
public class TestContainersConfig {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer =
            new PostgreSQLContainer<>("postgres:17.3-alpine3.21");

    @Container
    private static final ConfluentKafkaContainer kafkaContainer
            = new ConfluentKafkaContainer("confluentinc/cp-kafka:7.8.0");

    @DynamicPropertySource
    public static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");

        registry.add("app.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
        registry.add("app.kafka.consumer.topic", () -> "test-consumer-topic");
        registry.add("app.kafka.consumer.group-id", () -> "test-group");
        registry.add("app.kafka.consumer.session-timeout", () -> "30000");
        registry.add("app.kafka.consumer.max-partition-fetch-bytes", () -> "1048576");
        registry.add("app.kafka.consumer.max-poll-records", () -> "500");
        registry.add("app.kafka.consumer.max-poll-interval", () -> "300000");
        registry.add("app.kafka.consumer.auto-offset-reset", () -> "earliest");

        registry.add("app.kafka.producer.topic", () -> "test-producer-topic");
        registry.add("app.kafka.producer.acknowledgement", () -> "1");
    }
}