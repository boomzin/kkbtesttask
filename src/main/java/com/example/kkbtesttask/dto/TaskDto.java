package com.example.kkbtesttask.dto;

import com.example.kkbtesttask.model.Status;

import java.util.Objects;

public class TaskDto {

    private String name;
    private String description;
    private Status status;

    public TaskDto(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskDto taskDto = (TaskDto) o;
        return name.equals(taskDto.name) && description.equals(taskDto.description) && status == taskDto.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, status);
    }
}
