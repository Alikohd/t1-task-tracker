package com.example.t1tasktracker.service;

import com.example.t1tasktracker.dto.TaskNotificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaNotificationConsumer {
    private final NotificationService notificationService;

    @KafkaListener(containerFactory = "kafkaListenerContainerFactory",
            topics = "${app.kafka.consumer.topic}", groupId = "${app.kafka.consumer.group-id}")
    public void listenTaskStatusUpdate(List<TaskNotificationDto> messageList,
                                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                       @Header(KafkaHeaders.RECEIVED_KEY) String key,
                                       Acknowledgment ack) {
        for (TaskNotificationDto taskNotification : messageList) {
            try {
                log.debug("Processing message {} with key {} in topic {}", taskNotification, key, topic);
                notificationService.notifyTaskStatusUpdate(taskNotification);
            } catch (Exception e) {
                log.error("Failed to process message {} with key {} in topic {}", taskNotification, key, topic, e);
//                may sent message to DLQ
            }
        }

        log.debug("Batch processed, commiting offset..");
        ack.acknowledge();
    }
}
