package com.my.dto;

import java.time.LocalDate;

import com.my.entities.Task;

public class TaskCreateDto {

	private String title;
	private String description;
	private LocalDate dueDate;
	private Long assignedToEmployeeId;
	private Task.Status status = Task.Status.PENDING;
	private Task.Priority priority = Task.Priority.MEDIUM;

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

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public Long getAssignedToEmployeeId() {
		return assignedToEmployeeId;
	}

	public void setAssignedToEmployeeId(Long assignedToEmployeeId) {
		this.assignedToEmployeeId = assignedToEmployeeId;
	}

	public Task.Status getStatus() {
		return status;
	}

	public void setStatus(Task.Status status) {
		this.status = status;
	}

	public Task.Priority getPriority() {
		return priority;
	}

	public void setPriority(Task.Priority priority) {
		this.priority = priority;
	}
}
