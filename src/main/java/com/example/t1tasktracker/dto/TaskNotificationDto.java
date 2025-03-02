package com.example.t1tasktracker.dto;

import com.example.t1tasktracker.entity.TaskStatus;

public record TaskNotificationDto(Long id, TaskStatus taskStatus) {
}
