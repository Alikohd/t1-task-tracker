package com.example.t1tasktracker.dto;

import com.example.t1tasktracker.entity.TaskStatus;

public record TaskRequestDto(String title, String description, Long userId, TaskStatus status) {
}
