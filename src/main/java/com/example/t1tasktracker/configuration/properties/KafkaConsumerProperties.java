package com.example.t1tasktracker.configuration.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.kafka.consumer")
@RequiredArgsConstructor
@Getter
public class KafkaConsumerProperties {

    @Value("${app.kafka.bootstrap-servers}")
    private final String bootstrapServers;
    private final String topic;
    private final String groupId;
    private final String sessionTimeout;
    private final String maxPartitionFetchBytes;
    private final String maxPollRecords;
    private final String maxPollInterval;
    private final String autoOffsetReset;

}
