package com.example.kkbtesttask.web;

import com.example.kkbtesttask.dto.TaskDto;
import com.example.kkbtesttask.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = TaskController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name="Контроллер для работы с задачами",
        description="Позволяет просматривать список всех задач и отдельную задачу по номеру, создавать, редактировать и удалять задачи")
public class TaskController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TaskController.class);
    static final String REST_URL = "/api/tasks";

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Просмотр списка задач")
    public List<TaskDto> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Возвращает задачу по номеру, если ее не существует возвращает соответствующую ошибку")
    public TaskDto get(@PathVariable int id) {
        log.info("get task {}", id);
        return service.getById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Создание новой задачи")
    public ResponseEntity<TaskDto> createWithLocation(@Valid @RequestBody TaskDto task) {
        log.info("create {}", task.getName());
        TaskDto created = service.create(task);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление задачи")
    public HttpStatus delete(@PathVariable int id) {
        log.info("delete {}", id);
        return service.deleteExisted(id)? HttpStatus.OK : HttpStatus.NOT_FOUND;
    }

    @Transactional
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Редактирование задачи")
    public HttpStatus update(@Valid @RequestBody TaskDto taskDto, @PathVariable int id) {
        log.info("update task with id={}", id);
        service.update(taskDto, id);
        return HttpStatus.OK;
    }
}
