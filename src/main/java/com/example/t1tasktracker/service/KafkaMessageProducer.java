package com.example.t1tasktracker.service;

import com.example.t1tasktracker.configuration.properties.KafkaProducerProperties;
import com.example.t1tasktracker.dto.TaskNotificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaMessageProducer implements MessageProducer {
    private final KafkaTemplate<Long, TaskNotificationDto> kafkaTemplate;
    private final KafkaProducerProperties kafkaProducerProperties;

    @Override
    public void sendTaskStatusUpdate(TaskNotificationDto taskNotificationDto) {
        try {
            kafkaTemplate.send(kafkaProducerProperties.getTopic(), taskNotificationDto.id(), taskNotificationDto)
                    .whenComplete((result, exception) -> {
                        if (exception != null) {
                            log.error("Failed to send message. Topic = {}, Message = {}",
                                    kafkaProducerProperties.getTopic(), taskNotificationDto, exception);
                        } else {
                            log.info("Message sent successfully: {}", result.getRecordMetadata());
                        }
                    });
        } catch (Exception e) {
            log.error("Failed to send message.", e);
        }
    }
}
