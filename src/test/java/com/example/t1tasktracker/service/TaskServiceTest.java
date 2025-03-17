package com.example.t1tasktracker.service;

import com.example.t1tasktracker.dto.ResponseTaskDto;
import com.example.t1tasktracker.dto.TaskNotificationDto;
import com.example.t1tasktracker.dto.TaskRequestDto;
import com.example.t1tasktracker.entity.Task;
import com.example.t1tasktracker.entity.TaskStatus;
import com.example.t1tasktracker.exception.TaskNotFoundException;
import com.example.t1tasktracker.mapper.TaskMapper;
import com.example.t1tasktracker.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.example.t1tasktracker.utils.TaskTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private MessageProducer messageProducer;

    @InjectMocks
    private TaskService taskService;

    @Test
    void getTaskById_ShouldReturnResponseTaskDto_WhenTaskExists() {
        Long taskId = 1L;
        Task task = getTask(taskId);
        ResponseTaskDto expected = getResponseTaskDto(taskId);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskMapper.entityToDto(task)).thenReturn(expected);

        ResponseTaskDto actual = taskService.getTaskById(taskId);

        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskMapper, times(1)).entityToDto(task);
    }

    @Test
    void getTaskById_ShouldThrowException_WhenTaskDoesNotExist() {
        Long taskId = 100L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(taskId));
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    void getAllTasks_ShouldReturnListOfTaskDtos_WhenTaskExists() {
        Long taskId = 1L;
        Task task = getTask(taskId);
        ResponseTaskDto responseTaskDto = getResponseTaskDto(taskId);
        List<ResponseTaskDto> expected = List.of(responseTaskDto);
        when(taskRepository.findAll()).thenReturn(List.of(task));
        when(taskMapper.entityToDto(task)).thenReturn(responseTaskDto);

        List<ResponseTaskDto> actual = taskService.getAllTasks();

        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals(expected, actual);
        verify(taskRepository, times(1)).findAll();
        verify(taskMapper, times(1)).entityToDto(task);
    }

    @Test
    void addTask_ShouldReturnResponseTaskDto_WhenTaskExists() {
        Long taskId = 1L;
        TaskRequestDto taskRequest = getTaskRequestDto(TaskStatus.TO_DO);
        ResponseTaskDto responseTaskDto = getResponseTaskDto(taskId);
        Task task = new Task();
        task.setTitle("Test task");
        task.setDescription("Test description");
        task.setUserId(1L);
        task.setStatus(TaskStatus.TO_DO);
        Task savedTask = getTask(taskId);
        when(taskMapper.createDtoToEntity(taskRequest)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(savedTask);
        when(taskMapper.entityToDto(savedTask)).thenReturn(responseTaskDto);

        ResponseTaskDto actual = taskService.addTask(taskRequest);

        assertNotNull(actual);
        assertEquals(responseTaskDto, actual);
        verify(taskRepository, times(1)).save(task);
        verify(taskMapper, times(1)).createDtoToEntity(taskRequest);
        verify(taskMapper, times(1)).entityToDto(savedTask);
    }

    @Test
    void updateTask_ShouldThrowException_WhenTaskDoesNotExist() {
        Long taskId = 100L;
        TaskRequestDto taskUpdateRequest = getTaskRequestDto(TaskStatus.DONE);
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(taskId, taskUpdateRequest));
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    void updateTask_ShouldUpdateAndSendNotification_WhenTaskStatusChanges() {
        Long taskId = 1L;
        TaskRequestDto taskUpdateRequest = getTaskRequestDto(TaskStatus.DONE);
        Task existingTask = getTask(taskId);
        Task updatedTask = getTask(taskId);
        updatedTask.setStatus(TaskStatus.DONE);
        ResponseTaskDto taskResponse = getResponseTaskDto(taskId);
        TaskNotificationDto notificationDto = new TaskNotificationDto(taskId, taskUpdateRequest.status());

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(argThat(task  -> task.getId().equals(updatedTask.getId())))).thenReturn(updatedTask);
        when(taskMapper.entityToDto(updatedTask)).thenReturn(taskResponse);

        ResponseTaskDto actual = taskService.updateTask(taskId, taskUpdateRequest);

        assertNotNull(actual);
        assertEquals(taskResponse, actual);
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskMapper, times(1)).entityToDto(updatedTask);
        verify(messageProducer, times(1)).sendTaskStatusUpdate(notificationDto);
    }

    @Test
    void deleteTask_ShouldDeleteTask() {
        Long taskId = 1L;

        taskService.deleteTask(taskId);

        verify(taskRepository, times(1)).deleteById(taskId);
    }
}