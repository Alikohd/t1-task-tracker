package com.example.t1tasktracker.utils;

import com.example.t1tasktracker.dto.ResponseTaskDto;
import com.example.t1tasktracker.dto.TaskRequestDto;
import com.example.t1tasktracker.entity.Task;
import com.example.t1tasktracker.entity.TaskStatus;

public class TaskTestData {
    public static Task getTask(Long id) {
        Task task = new Task();
        task.setId(id);
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setUserId(1L);
        task.setStatus(TaskStatus.TO_DO);
        return task;
    }

    public static ResponseTaskDto getResponseTaskDto(Long taskId) {
        return new ResponseTaskDto(taskId, "Test Task", "Test Description", 1L, TaskStatus.TO_DO);
    }

    public static TaskRequestDto getTaskRequestDto(TaskStatus taskStatus) {
        return new TaskRequestDto("Test Task", "Test Description", 1L, taskStatus);
    }

    public static TaskRequestDto getUpdateTaskDto(TaskStatus taskStatus) {
        return new TaskRequestDto("Updated Task", "Update Description", 1L, taskStatus);
    }
}
