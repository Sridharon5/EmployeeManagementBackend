package com.my.dto;

import java.time.LocalDate;

public class TaskUpdateDto {

    private Integer id; 
    private String title;
    private String description;
    private Long assignedToId;
    private Long evaluatorId;
    private LocalDate dueDate;
    private String status; 

    public TaskUpdateDto() {
    }

    public TaskUpdateDto(Integer id, String title, String description, Long assignedToId, Long evaluatorId, LocalDate dueDate, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.assignedToId = assignedToId;
        this.evaluatorId = evaluatorId;
        this.dueDate = dueDate;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(Long assignedToId) {
        this.assignedToId = assignedToId;
    }

    public Long getEvaluatorId() {
        return evaluatorId;
    }

    public void setEvaluatorId(Long evaluatorId) {
        this.evaluatorId = evaluatorId;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
