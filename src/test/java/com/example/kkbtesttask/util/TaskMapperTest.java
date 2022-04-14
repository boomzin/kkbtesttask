package com.example.kkbtesttask.util;

import com.example.kkbtesttask.dto.TaskDto;
import com.example.kkbtesttask.model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static com.example.kkbtesttask.TestUtil.*;

class TaskMapperTest {

    @Autowired
    private final TaskMapper taskMapper = new TaskMapperImpl();

    @Test
    void toTaskDto() {
        Task task = getNewTask();

        TaskDto taskDto = taskMapper.toTaskDto(task);

        Assertions.assertEquals(task.getId(), taskDto.getId());
        Assertions.assertEquals(task.getName(), taskDto.getName());
        Assertions.assertEquals(task.getDescription(), taskDto.getDescription());
        Assertions.assertEquals(task.getStatus(), taskDto.getStatus());
    }

    @Test
    void dtoToTask() {
        TaskDto taskDto = getNewTaskDto();

        Task task = taskMapper.dtoToTask(taskDto);

        Assertions.assertEquals(taskDto.getId(), task.getId());
        Assertions.assertEquals(taskDto.getName(), task.getName());
        Assertions.assertEquals(taskDto.getDescription(), task.getDescription());
        Assertions.assertEquals(taskDto.getStatus(), task.getStatus());
    }

    @Test
    void toListTaskDto() {
        List<Task> tasks = new ArrayList<>();
        Task task = getNewTask();
        tasks.add(task);

        List<TaskDto> dtos = taskMapper.toListTaskDto(tasks);

        Assertions.assertNotNull(dtos);
        Assertions.assertEquals(1, dtos.size());
        Assertions.assertEquals(task.getId(), dtos.get(0).getId());
        Assertions.assertEquals(task.getName(), dtos.get(0).getName());
        Assertions.assertEquals(task.getDescription(), dtos.get(0).getDescription());
        Assertions.assertEquals(task.getStatus(), dtos.get(0).getStatus());
    }
}