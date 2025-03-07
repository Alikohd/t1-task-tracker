package com.example.t1tasktracker.service;

import com.example.t1tasktracker.dto.TaskNotificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaNotificationConsumer {
    private final NotificationService notificationService;

    @KafkaListener(containerFactory = "kafkaListenerContainerFactory",
            topics = "${app.kafka.consumer.topic}", groupId = "${app.kafka.consumer.group-id}")
    public void listenTaskStatusUpdate(List<ConsumerRecord<Long, TaskNotificationDto>> records, Acknowledgment ack) {
        for (var record : records) {
            try {
                log.debug("Processing message {} with key {} in topic {}", record.value(), record.key(), record.topic());
                notificationService.notifyTaskStatusUpdate(record.value());
            } catch (Exception e) {
                log.error("Failed to process message {} with key {} in topic {}", record.value(), record.key(), record.topic(), e);
//                may sent message to DLQ
            }
        }

        ack.acknowledge();
        log.info("Batch processed, offset commited for topic {}, batchSize {}", records.get(0).topic(), records.size());
    }
}
