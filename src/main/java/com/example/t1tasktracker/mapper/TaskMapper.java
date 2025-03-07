package com.example.t1tasktracker.mapper;

import com.example.t1tasktracker.dto.ResponseTaskDto;
import com.example.t1tasktracker.dto.TaskRequestDto;
import com.example.t1tasktracker.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMapper {

    Task createDtoToEntity(TaskRequestDto taskRequestDto);

    ResponseTaskDto entityToDto(Task taskEntity);
}
