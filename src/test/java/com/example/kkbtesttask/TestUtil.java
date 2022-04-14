package com.example.kkbtesttask;

import com.example.kkbtesttask.dto.TaskDto;
import com.example.kkbtesttask.model.Status;
import com.example.kkbtesttask.model.Task;
import com.example.kkbtesttask.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUtil {
    public static final int TASK1_ID = 1;
    public static final int TASK2_ID = 2;
    public static final int NOT_EXISTED_TASK_ID = 15;
    public static final Task task = new Task(TASK1_ID, "Make a task list", "main task", Status.DONE, "Some data");

    public static Task getNewTask() {
        Task task = new Task();
        task.setId(1);
        task.setName("name");
        task.setDescription("description");
        task.setStatus(Status.OPEN);
        task.setBusinessData("some data");
        return task;
    }

    public static TaskDto getNewTaskDto() {
        return new TaskDto(11, "dto name", "dto description", Status.OPEN);
    }

    public static TaskDto getUpdated() {
        return new TaskDto(TASK1_ID, "updated name", "updated description", Status.OPEN);
    }

    public static void assertNoDataEquals(TaskDto actual, Task expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("businessData").isEqualTo(expected);
    }

    public static TaskDto asTaskDto(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        String jsonActual = mvcResult.getResponse().getContentAsString();
        return JsonUtil.readValue(jsonActual, TaskDto.class);
    }
}
