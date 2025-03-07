package com.example.t1tasktracker.configuration;

import com.example.t1tasktracker.configuration.properties.KafkaProducerProperties;
import com.example.t1tasktracker.dto.TaskNotificationDto;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig {
    private final KafkaProducerProperties kafkaProducerProperties;

    @Bean
    public ProducerFactory<Long, TaskNotificationDto> producerFactory() {
        Map<String, Object> props = new HashMap<>();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProducerProperties.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, false);

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean(name = "taskNotificationProducer")
    public KafkaTemplate<Long, TaskNotificationDto> kafkaTemplate(ProducerFactory<Long, TaskNotificationDto> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

}
