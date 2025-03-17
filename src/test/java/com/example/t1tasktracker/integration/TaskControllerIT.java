package com.example.t1tasktracker.integration;

import com.example.t1tasktracker.dto.ResponseTaskDto;
import com.example.t1tasktracker.dto.TaskRequestDto;
import com.example.t1tasktracker.entity.TaskStatus;
import com.example.t1tasktracker.service.TaskService;
import com.example.t1tasktracker.utils.TaskTestData;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerIT extends TestContainersConfig {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TaskService taskService;

    @Test
    void addTask_ShouldSaveAndReturnTask() throws Exception {
        TaskRequestDto taskRequest = TaskTestData.getTaskRequestDto(TaskStatus.TO_DO);
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("title").value(taskRequest.title()))
                .andExpect(jsonPath("description").value(taskRequest.description()))
                .andExpect(jsonPath("userId").value(taskRequest.userId()))
                .andExpect(jsonPath("status").value(taskRequest.status().name()));
    }

    @Test
    void getTaskById_ShouldReturnTask() throws Exception {
        TaskRequestDto taskRequest = TaskTestData.getTaskRequestDto(TaskStatus.TO_DO);
        ResponseTaskDto responseTaskDto = taskService.addTask(taskRequest);

        mockMvc.perform(get("/tasks/" + responseTaskDto.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(responseTaskDto.id()))
                .andExpect(jsonPath("title").value(taskRequest.title()))
                .andExpect(jsonPath("description").value(taskRequest.description()))
                .andExpect(jsonPath("userId").value(taskRequest.userId()))
                .andExpect(jsonPath("status").value(taskRequest.status().name()));
    }

    @Test
    void updateTask_ShouldUpdateTask() throws Exception {
        TaskRequestDto taskRequest = TaskTestData.getTaskRequestDto(TaskStatus.TO_DO);
        TaskRequestDto taskUpdateRequest = TaskTestData.getUpdateTaskDto(TaskStatus.DONE);
        ResponseTaskDto responseTaskDto = taskService.addTask(taskRequest);

        mockMvc.perform(put("/tasks/" + responseTaskDto.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskUpdateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("title").value(taskUpdateRequest.title()))
                .andExpect(jsonPath("description").value(taskUpdateRequest.description()))
                .andExpect(jsonPath("userId").value(taskUpdateRequest.userId()))
                .andExpect(jsonPath("status").value(taskUpdateRequest.status().name()));
    }

    @Test
    void deleteTask_ShouldDeleteTask() throws Exception {
        TaskRequestDto taskRequest = TaskTestData.getTaskRequestDto(TaskStatus.TO_DO);
        ResponseTaskDto responseTaskDto = taskService.addTask(taskRequest);

        mockMvc.perform(delete("/tasks/" + responseTaskDto.id()))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAllTasks_ShouldReturnListOfAllTasks() throws Exception {
        TaskRequestDto taskRequest = TaskTestData.getTaskRequestDto(TaskStatus.TO_DO);
        taskService.addTask(taskRequest);
        TaskRequestDto secondTaskRequest = TaskTestData.getTaskRequestDto(TaskStatus.BACKLOG);
        taskService.addTask(secondTaskRequest);

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getTaskById_ShouldThrowException_WhenTaskDoesNotExist() throws Exception {
        Long nonExistentId = 100L;

        mockMvc.perform(get("/tasks/" + nonExistentId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value(String.format("Task with id %d not found", nonExistentId)));
    }
}

