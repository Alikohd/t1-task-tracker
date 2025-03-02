package com.example.t1tasktracker.dto;

import com.example.t1tasktracker.entity.TaskStatus;

public record ResponseTaskDto(Long id, String title, String description, Long userId, TaskStatus status) {
}
