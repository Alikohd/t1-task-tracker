package com.example.t1tasktracker.service;

import com.example.t1tasktracker.dto.TaskNotificationDto;

public interface NotificationService {

    void notifyTaskStatusUpdate(TaskNotificationDto taskNotificationDto);
}
