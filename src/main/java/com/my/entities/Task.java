package com.my.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "task")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Employee assignedTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluator_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Employee evaluator;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('PENDING','IN_PROGRESS','COMPLETED','OVERDUE','REJECTED') DEFAULT 'PENDING'")
    private Status status = Status.PENDING;

    /** LOW → CRITICAL sort order uses {@link Priority#ordinal()} via {@link #prioritySort}. */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('LOW','MEDIUM','HIGH','CRITICAL') DEFAULT 'MEDIUM'")
    private Priority priority = Priority.MEDIUM;

    @Column(name = "priority_sort", nullable = false)
    private int prioritySort = Priority.MEDIUM.ordinal();

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "closed_at")
    private LocalDateTime closedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "closed_by_user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "password", "authorities"})
    private User closedBy;

    @Column(name = "completion_note", columnDefinition = "TEXT")
    private String completionNote;

    public enum Status {
        PENDING,
        IN_PROGRESS,
        COMPLETED,
        OVERDUE,
        REJECTED
    }

    public enum Priority {
        LOW,
        MEDIUM,
        HIGH,
        CRITICAL
    }

    public Task() {
    }

    public Task(String title, String description, Employee assignedTo, Employee evaluator, LocalDate dueDate, Status status) {
        this.title = title;
        this.description = description;
        this.assignedTo = assignedTo;
        this.evaluator = evaluator;
        this.dueDate = dueDate;
        this.status = status;
    }

    @PrePersist
    @PreUpdate
    private void syncPrioritySort() {
        if (priority == null) {
            priority = Priority.MEDIUM;
        }
        this.prioritySort = priority.ordinal();
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

    public Employee getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Employee assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Employee getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(Employee evaluator) {
        this.evaluator = evaluator;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority != null ? priority : Priority.MEDIUM;
        this.prioritySort = this.priority.ordinal();
    }

    @JsonIgnore
    public int getPrioritySort() {
        return prioritySort;
    }

    public void setPrioritySort(int prioritySort) {
        this.prioritySort = prioritySort;
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

    public User getClosedBy() {
        return closedBy;
    }

    public void setClosedBy(User closedBy) {
        this.closedBy = closedBy;
    }

    public String getCompletionNote() {
        return completionNote;
    }

    public void setCompletionNote(String completionNote) {
        this.completionNote = completionNote;
    }
}
