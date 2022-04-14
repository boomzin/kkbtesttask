package com.example.kkbtesttask.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Persistable;
import org.springframework.data.util.ProxyUtils;

import javax.persistence.*;

@Entity
@Table(name = "task")
@Access(AccessType.FIELD)
@Schema(description = "Сущность задачи")
public class Task implements Persistable<Integer> {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Override
    public Integer getId() {
        return this.id;
    }

    @JsonIgnore
    @Override
    public boolean isNew() {
        return id == null;
    }

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    @Schema(defaultValue = "OPEN")
    private Status status;

    @Column(name = "business_data")
    @Schema(description = "Поле только для иллюстрации разницы между сущностью модели данных и transfer object")
    private String businessData;

    public Task() {
    }

    public Task(Integer id, String name, String description, Status status, String businessData) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.businessData = businessData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(ProxyUtils.getUserClass(o))) {
            return false;
        }
        Task that = (Task) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id;
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

    public String getBusinessData() {
        return businessData;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public void setBusinessData(String businessData) {
        this.businessData = businessData;
    }
}
