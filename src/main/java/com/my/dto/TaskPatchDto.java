package com.my.dto;

import java.time.LocalDate;

public class TaskPatchDto {

	private String title;
	private String description;
	private Long assignedToEmployeeId;
	private Long evaluatorEmployeeId;
	private LocalDate dueDate;
	private String status;
	private String completionNote;

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

	public Long getAssignedToEmployeeId() {
		return assignedToEmployeeId;
	}

	public void setAssignedToEmployeeId(Long assignedToEmployeeId) {
		this.assignedToEmployeeId = assignedToEmployeeId;
	}

	public Long getEvaluatorEmployeeId() {
		return evaluatorEmployeeId;
	}

	public void setEvaluatorEmployeeId(Long evaluatorEmployeeId) {
		this.evaluatorEmployeeId = evaluatorEmployeeId;
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

	public String getCompletionNote() {
		return completionNote;
	}

	public void setCompletionNote(String completionNote) {
		this.completionNote = completionNote;
	}
}
