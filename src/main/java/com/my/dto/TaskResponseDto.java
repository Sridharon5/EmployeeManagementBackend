package com.my.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.my.entities.Task;
import com.my.entities.User;

public class TaskResponseDto {

	private Long id;
	private String title;
	private String description;
	private LocalDate dueDate;
	private String status;
	private String priority;
	private Long assignedToEmployeeId;
	private Long assignedToUserId;
	/** From assignee's linked {@link User}; used for display (first required in UI logic, last optional). */
	private String assigneeFirstName;
	private String assigneeLastName;
	private String assigneeUsername;
	private Long evaluatorEmployeeId;
	private Long evaluatorUserId;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime closedAt;
	private Long closedByUserId;
	private String completionNote;

	public static TaskResponseDto fromEntity(Task t) {
		TaskResponseDto d = new TaskResponseDto();
		d.setId(t.getId());
		d.setTitle(t.getTitle());
		d.setDescription(t.getDescription());
		d.setDueDate(t.getDueDate());
		d.setStatus(t.getStatus() != null ? t.getStatus().name() : null);
		d.setPriority(t.getPriority() != null ? t.getPriority().name() : null);
		d.setCreatedAt(t.getCreatedAt());
		d.setUpdatedAt(t.getUpdatedAt());
		d.setClosedAt(t.getClosedAt());
		d.setCompletionNote(t.getCompletionNote());
		if (t.getAssignedTo() != null) {
			d.setAssignedToEmployeeId(t.getAssignedTo().getId());
			User assigneeUser = t.getAssignedTo().getUser();
			if (assigneeUser != null) {
				d.setAssignedToUserId(assigneeUser.getId());
				d.setAssigneeFirstName(assigneeUser.getFirstName());
				d.setAssigneeLastName(assigneeUser.getLastName());
				d.setAssigneeUsername(assigneeUser.getUsername());
			}
		}
		if (t.getEvaluator() != null) {
			d.setEvaluatorEmployeeId(t.getEvaluator().getId());
			if (t.getEvaluator().getUser() != null) {
				d.setEvaluatorUserId(t.getEvaluator().getUser().getId());
			}
		}
		if (t.getClosedBy() != null) {
			d.setClosedByUserId(t.getClosedBy().getId());
		}
		return d;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Long getAssignedToEmployeeId() {
		return assignedToEmployeeId;
	}

	public void setAssignedToEmployeeId(Long assignedToEmployeeId) {
		this.assignedToEmployeeId = assignedToEmployeeId;
	}

	public Long getAssignedToUserId() {
		return assignedToUserId;
	}

	public void setAssignedToUserId(Long assignedToUserId) {
		this.assignedToUserId = assignedToUserId;
	}

	public String getAssigneeFirstName() {
		return assigneeFirstName;
	}

	public void setAssigneeFirstName(String assigneeFirstName) {
		this.assigneeFirstName = assigneeFirstName;
	}

	public String getAssigneeLastName() {
		return assigneeLastName;
	}

	public void setAssigneeLastName(String assigneeLastName) {
		this.assigneeLastName = assigneeLastName;
	}

	public String getAssigneeUsername() {
		return assigneeUsername;
	}

	public void setAssigneeUsername(String assigneeUsername) {
		this.assigneeUsername = assigneeUsername;
	}

	public Long getEvaluatorEmployeeId() {
		return evaluatorEmployeeId;
	}

	public void setEvaluatorEmployeeId(Long evaluatorEmployeeId) {
		this.evaluatorEmployeeId = evaluatorEmployeeId;
	}

	public Long getEvaluatorUserId() {
		return evaluatorUserId;
	}

	public void setEvaluatorUserId(Long evaluatorUserId) {
		this.evaluatorUserId = evaluatorUserId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public LocalDateTime getClosedAt() {
		return closedAt;
	}

	public void setClosedAt(LocalDateTime closedAt) {
		this.closedAt = closedAt;
	}

	public Long getClosedByUserId() {
		return closedByUserId;
	}

	public void setClosedByUserId(Long closedByUserId) {
		this.closedByUserId = closedByUserId;
	}

	public String getCompletionNote() {
		return completionNote;
	}

	public void setCompletionNote(String completionNote) {
		this.completionNote = completionNote;
	}
}
