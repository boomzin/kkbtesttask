package com.example.kkbtesttask.util;

import com.example.kkbtesttask.dto.TaskDto;
import com.example.kkbtesttask.model.Task;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TaskMapper {

    TaskDto toTaskDto(Task task);

    Task dtoToTask(TaskDto taskDto);

    List<TaskDto> toListTaskDto(List<Task> tasks);
}
