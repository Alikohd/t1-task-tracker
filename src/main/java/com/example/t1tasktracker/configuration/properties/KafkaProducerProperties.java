package com.example.t1tasktracker.configuration.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.kafka.producer")
@RequiredArgsConstructor
@Getter

public class KafkaProducerProperties {

    @Value("${app.kafka.bootstrap-servers}")
    private final String bootstrapServers;
    private final String topic;
    private final String acknowledgement;

}