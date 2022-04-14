package com.example.kkbtesttask.web;

import com.example.kkbtesttask.TestUtil;
import com.example.kkbtesttask.dto.TaskDto;
import com.example.kkbtesttask.model.Task;
import com.example.kkbtesttask.repository.TaskRepository;
import com.example.kkbtesttask.util.JsonUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.kkbtesttask.TestUtil.*;
import static com.example.kkbtesttask.util.JsonUtil.writeValue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class TaskControllerTest {
    static final String URL = "/api/tasks/";

    @Autowired
    private TaskRepository repository;

    @Autowired
    protected MockMvc mockMvc;

    protected ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder);
    }

    @Test
    void getAll() throws Exception {
        MvcResult result = perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        List<TaskDto> dtos = JsonUtil.readValues(content, TaskDto.class);
        List<Task> tasks = repository.findAll(Sort.by(Sort.Direction.ASC, "status", "name"));
        for (int i = 0; i < dtos.size(); i++) {
            Assertions.assertEquals(tasks.get(i).getId(), dtos.get(i).getId());
            Assertions.assertEquals(tasks.get(i).getName(), dtos.get(i).getName());
            Assertions.assertEquals(tasks.get(i).getDescription(), dtos.get(i).getDescription());
            Assertions.assertEquals(tasks.get(i).getStatus(), dtos.get(i).getStatus());
        }
    }

    @Test
    void get() throws Exception {
        MvcResult result = perform(MockMvcRequestBuilders.get(URL + TASK1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        TaskDto taskDto = JsonUtil.readValue(content, TaskDto.class);
        TestUtil.assertNoDataEquals(taskDto, task);

    }

    @Test
    void getNotExisted() throws Exception {
        MvcResult result = perform(MockMvcRequestBuilders.get(URL + NOT_EXISTED_TASK_ID))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertTrue(content.contains("Task with id=" + NOT_EXISTED_TASK_ID + " does not exist"));
    }

    @Test
    void createWithLocation() throws Exception {
        TaskDto newTask = TestUtil.getNewTaskDto();
        newTask.setId(null);
        TaskDto created = asTaskDto(perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newTask)))
                .andExpect(status().isCreated()).andReturn());
        int newId = created.getId();
        TestUtil.assertNoDataEquals(created, repository.findById(newId).get());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(URL + TASK1_ID))
                .andExpect(status().isOk());
        Assertions.assertFalse(repository.findById(TASK1_ID).isPresent());
        Assertions.assertTrue(repository.findById(TASK2_ID).isPresent());
    }

    @Test
    void deleteNotExistedTask() throws Exception {
        perform(MockMvcRequestBuilders.delete(URL + NOT_EXISTED_TASK_ID))
                .andExpect(status().isOk())
                .andExpect(content().string("\"NOT_FOUND\""));
    }

    @Test
    void update() throws Exception {
        TaskDto updated = TestUtil.getUpdated();
        perform(MockMvcRequestBuilders.put(URL + TASK1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updated)))
                .andDo(print())
                .andExpect(status().isOk());
        TestUtil.assertNoDataEquals(updated, repository.findById(TASK1_ID).get());
    }
}