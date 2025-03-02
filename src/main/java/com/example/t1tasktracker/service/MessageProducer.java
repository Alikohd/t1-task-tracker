package com.example.t1tasktracker.service;

import com.example.t1tasktracker.dto.TaskNotificationDto;

public interface MessageProducer {

    void sendTaskStatusUpdate(TaskNotificationDto taskNotificationDto);
}
