package com.example.kkbtesttask.service;

import com.example.kkbtesttask.dto.TaskDto;
import com.example.kkbtesttask.error.IllegalRequestDataException;
import com.example.kkbtesttask.model.Status;
import com.example.kkbtesttask.model.Task;
import com.example.kkbtesttask.repository.TaskRepository;
import com.example.kkbtesttask.util.TaskMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class TaskService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository repository;
    private final TaskMapper mapper;


    public TaskService(TaskRepository repository, TaskMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<TaskDto> getAll() {
        log.info("getAll");
        return mapper.toListTaskDto(repository.findAll(Sort.by(Sort.Direction.ASC, "status", "name")));
    }

    public TaskDto getById(int id) {
        log.info("get task {}", id);
        return mapper.toTaskDto(repository.findById(id).orElseThrow(
                () -> new IllegalRequestDataException("Task with id=" + id + " does not exist")));
    }

    public TaskDto create(TaskDto taskDto) {
        log.info("create {}", taskDto.getName());
        if (taskDto.getId() != null) {
            throw new IllegalRequestDataException("Task must be new (id=null)");
        }
        taskDto.setStatus(Status.OPEN);
        Task task = mapper.dtoToTask(taskDto);
        task.setBusinessData("Some new business data");
        return mapper.toTaskDto(repository.save(task));
    }

    @Transactional
    public boolean deleteExisted(@PathVariable int id) {
        log.info("delete {}", id);
        return repository.delete(id) != 0;
    }

    @Transactional
    public void update(TaskDto taskDto, int id) {
        log.info("update task with id={}", id);
        if (taskDto.getId() == null) {
            taskDto.setId(id);
        } else if (taskDto.getId() != id) {
            throw new IllegalRequestDataException("Task must has id=" + id);
        }
        Task updated = repository.findById(id).orElseThrow(
                () -> new IllegalRequestDataException("Task with id=" + id + " does not exist"));
        updated.setName(taskDto.getName());
        updated.setDescription(taskDto.getDescription());
        if (taskDto.getStatus() != null) {
            updated.setStatus(taskDto.getStatus());
        }
        repository.save(updated);
    }
}
