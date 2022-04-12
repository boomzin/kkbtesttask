package com.example.kkbtesttask.service;

import com.example.kkbtesttask.dto.TaskDto;
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

    //TODO check if not exist
    public TaskDto getById(int id) {
        log.info("get task {}", id);
        return mapper.toTaskDto(repository.findById(id).get());
    }

    // TODO add check new
    public TaskDto create(TaskDto taskDto) {
        log.info("create {}", taskDto.getName());
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


    // TODO add check id
    @Transactional
    public void update(TaskDto taskDto, int id) {
        log.info("update task with id={}", id);
        Task updated = repository.getById(id);
        updated.setName(taskDto.getName());
        updated.setDescription(taskDto.getDescription());
        updated.setStatus(taskDto.getStatus());
        repository.save(updated);
    }
}
