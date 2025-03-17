package com.example.t1tasktracker.mapper;

import com.example.t1tasktracker.dto.ResponseTaskDto;
import com.example.t1tasktracker.dto.TaskRequestDto;
import com.example.t1tasktracker.entity.Task;
import com.example.t1tasktracker.entity.TaskStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.example.t1tasktracker.utils.TaskTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

class TaskMapperTest {
    private final TaskMapper taskMapper = new TaskMapperImpl();

    @Test
    void toEntity() {
        Task expected = getTask(null);
        TaskRequestDto taskRequest = getTaskRequestDto(TaskStatus.TO_DO);

        Task actual = taskMapper.createDtoToEntity(taskRequest);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void toDto() {
        Long id = 1L;
        ResponseTaskDto expected = getResponseTaskDto(id);
        Task entity = getTask(id);

        ResponseTaskDto actual = taskMapper.entityToDto(entity);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

}