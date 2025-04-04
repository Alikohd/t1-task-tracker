package com.example.t1tasktracker.service;

import com.example.t1tasktracker.dto.ResponseTaskDto;
import com.example.t1tasktracker.dto.TaskNotificationDto;
import com.example.t1tasktracker.dto.TaskRequestDto;
import com.example.t1tasktracker.entity.Task;
import com.example.t1tasktracker.entity.TaskStatus;
import com.example.t1tasktracker.exception.TaskNotFoundException;
import com.example.t1tasktracker.mapper.TaskMapper;
import com.example.t1tasktracker.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.etu.t1logstarter.aspect.annotation.LogBefore;
import ru.etu.t1logstarter.aspect.annotation.LogException;
import ru.etu.t1logstarter.aspect.annotation.LogTimeExecution;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;
    private final MessageProducer messageProducer;

    @LogTimeExecution
    public ResponseTaskDto addTask(TaskRequestDto taskDto) {
        Task persistedTask = taskRepository.save(taskMapper.createDtoToEntity(taskDto));
        return taskMapper.entityToDto(persistedTask);
    }

    @LogException
    @LogBefore
    @LogTimeExecution
    public ResponseTaskDto getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(String.format("Task with id %d not found", id)));
        return taskMapper.entityToDto(task);
    }

    @LogBefore
    @LogException
    @LogTimeExecution
    public ResponseTaskDto updateTask(Long id, TaskRequestDto taskDto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(String.format("Task with id %d not found", id)));
        TaskStatus old_status = task.getStatus();

        task.setTitle(taskDto.title());
        task.setDescription(taskDto.description());
        task.setUserId(taskDto.userId());
        task.setStatus(taskDto.status());

        if (old_status != taskDto.status()) {
            messageProducer.sendTaskStatusUpdate(new TaskNotificationDto(id, taskDto.status()));
        }
        return taskMapper.entityToDto(taskRepository.save(task));
    }

    @LogTimeExecution
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @LogTimeExecution
    public List<ResponseTaskDto> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().map(taskMapper::entityToDto).toList();
    }
}
