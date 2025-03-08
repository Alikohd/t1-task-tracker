package com.example.t1tasktracker.controller;

import com.example.t1tasktracker.dto.ResponseTaskDto;
import com.example.t1tasktracker.dto.TaskRequestDto;
import com.example.t1tasktracker.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.etu.t1logstarter.aspect.annotation.LogReturning;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("tasks")
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @LogReturning
    public ResponseTaskDto addTask(@RequestBody TaskRequestDto taskDto) {
        return taskService.addTask(taskDto);
    }

    @GetMapping
    public List<ResponseTaskDto> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    @LogReturning
    public ResponseTaskDto getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @PutMapping("/{id}")
    @LogReturning
    public ResponseTaskDto updateTask(@PathVariable Long id, @RequestBody TaskRequestDto taskDto) {
        return taskService.updateTask(id, taskDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}
