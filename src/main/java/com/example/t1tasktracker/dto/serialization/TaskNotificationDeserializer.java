package com.example.t1tasktracker.dto.serialization;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class TaskNotificationDeserializer<T> extends JsonDeserializer<T> {

    private static String getMessage(byte[] data) {
        return new String(data, StandardCharsets.UTF_8);
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        try {
            return super.deserialize(topic, data);
        } catch (Exception e) {
            log.warn("Failed to deserialize message. Topic: {}, Message: {}", topic, getMessage(data));
            throw new SerializationException(String.format("Failed to deserialize message in topic %s", topic), e);
        }
    }

    @Override
    public T deserialize(String topic, Headers headers, byte[] data) {
        try {
            return super.deserialize(topic, data);
        } catch (Exception e) {
            log.warn("Failed to deserialize message. Topic: {}, Message: {}", topic, getMessage(data));
            throw new SerializationException(String.format("Failed to deserialize message in topic %s", topic), e);
        }
    }
}
