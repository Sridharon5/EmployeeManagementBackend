package com.my.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.my.dto.TaskCreateDto;
import com.my.dto.TaskPatchDto;
import com.my.dto.TaskResponseDto;
import com.my.dto.TaskUpdateDto;
import com.my.dto.UserDashboardDto;
import com.my.entities.Employee;
import com.my.entities.Role;
import com.my.entities.Task;
import com.my.entities.User;
import com.my.repositories.EmployeeRepository;
import com.my.repositories.TaskRepository;
import com.my.services.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

	private static final Set<Task.Status> OPEN_STATUSES = EnumSet.of(Task.Status.PENDING, Task.Status.IN_PROGRESS,
			Task.Status.OVERDUE);
	private static final Set<Task.Status> CLOSED_STATUSES = EnumSet.of(Task.Status.COMPLETED, Task.Status.REJECTED);

	private final TaskRepository taskRepository;
	private final EmployeeRepository employeeRepository;

	public TaskServiceImpl(TaskRepository taskRepository, EmployeeRepository employeeRepository) {
		this.taskRepository = taskRepository;
		this.employeeRepository = employeeRepository;
	}

	private static Pageable limitPageable(Pageable pageable) {
		int size = Math.min(pageable.getPageSize(), 100);
		return PageRequest.of(pageable.getPageNumber(), size, pageable.getSort());
	}

	@Override
	@Transactional
	public Task createTask(User currentUser, TaskCreateDto dto) {
		requireAdmin(currentUser);
		if (dto.getTitle() == null || dto.getTitle().isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "title is required");
		}
		if (dto.getDueDate() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "dueDate is required");
		}
		if (dto.getAssignedToEmployeeId() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "assignedToEmployeeId is required");
		}
		Employee assignee = employeeRepository.findById(dto.getAssignedToEmployeeId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Assignee employee not found"));
		Task task = new Task();
		task.setTitle(dto.getTitle().trim());
		task.setDescription(dto.getDescription());
		task.setDueDate(dto.getDueDate());
		task.setAssignedTo(assignee);
		task.setEvaluator(null);
		task.setStatus(dto.getStatus() != null ? dto.getStatus() : Task.Status.PENDING);
		task.setId(null);
		task.setCreatedAt(LocalDateTime.now());
		task.setUpdatedAt(LocalDateTime.now());
		return taskRepository.save(task);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TaskResponseDto> getAllTasksForAdmin(User currentUser) {
		requireAdmin(currentUser);
		return taskRepository.findAll().stream().map(TaskResponseDto::fromEntity).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TaskResponseDto> listTasks(User currentUser, String scope, Pageable pageable) {
		Pageable p = limitPageable(pageable);
		if ("all".equalsIgnoreCase(scope) && isAdmin(currentUser)) {
			return taskRepository.findAll(p).map(TaskResponseDto::fromEntity);
		}
		if ("my".equalsIgnoreCase(scope) || "all".equalsIgnoreCase(scope)) {
			return pageMyTasks(currentUser, p);
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "scope must be 'my' or 'all'");
	}

	private Page<TaskResponseDto> pageMyTasks(User currentUser, Pageable p) {
		Optional<Employee> emp = employeeRepository.findByUser_Id(currentUser.getId());
		if (emp.isEmpty()) {
			return Page.empty(p);
		}
		return taskRepository.findByAssignedTo_Id(emp.get().getId(), p).map(TaskResponseDto::fromEntity);
	}

	@Override
	@Transactional(readOnly = true)
	public TaskResponseDto getTaskById(User currentUser, Long id) {
		Task task = loadTaskDetailed(id);
		assertCanRead(currentUser, task);
		return TaskResponseDto.fromEntity(task);
	}

	@Override
	@Transactional
	public TaskResponseDto patchTask(User currentUser, Long id, TaskPatchDto patch) {
		Task task = loadTaskDetailed(id);
		assertCanRead(currentUser, task);
		if (isAdmin(currentUser)) {
			applyAdminPatch(task, patch, currentUser);
		} else {
			applyUserPatch(task, patch, currentUser);
		}
		task.setUpdatedAt(LocalDateTime.now());
		return TaskResponseDto.fromEntity(taskRepository.save(task));
	}

	@Override
	@Transactional
	public TaskResponseDto updateTask(User currentUser, Long id, TaskUpdateDto dto) {
		if (dto.getId() != null && !dto.getId().equals(id)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task id mismatch");
		}
		TaskPatchDto p = new TaskPatchDto();
		p.setTitle(dto.getTitle());
		p.setDescription(dto.getDescription());
		p.setAssignedToEmployeeId(dto.getAssignedToId());
		p.setEvaluatorEmployeeId(dto.getEvaluatorId());
		p.setDueDate(dto.getDueDate());
		p.setStatus(dto.getStatus());
		return patchTask(currentUser, id, p);
	}

	@Override
	@Transactional
	public void deleteTask(User currentUser, Long id) {
		requireAdmin(currentUser);
		if (!taskRepository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
		}
		taskRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public UserDashboardDto getMyDashboard(User currentUser) {
		UserDashboardDto dash = new UserDashboardDto();
		Optional<Employee> emp = employeeRepository.findByUser_Id(currentUser.getId());
		if (emp.isEmpty()) {
			dash.setOpenTaskCount(0);
			dash.setDueWithinSevenDaysCount(0);
			dash.setRecentlyClosedTasks(List.of());
			return dash;
		}
		Long empId = emp.get().getId();
		LocalDate today = LocalDate.now();
		LocalDate weekEnd = today.plusDays(6);
		dash.setOpenTaskCount(taskRepository.countByAssignedTo_IdAndStatusIn(empId, OPEN_STATUSES));
		dash.setDueWithinSevenDaysCount(
				taskRepository.countOpenTasksDueBetween(empId, OPEN_STATUSES, today, weekEnd));
		List<Task> recent = taskRepository
				.findTop5ByAssignedTo_IdAndStatusInAndClosedAtIsNotNullOrderByClosedAtDesc(empId, CLOSED_STATUSES);
		dash.setRecentlyClosedTasks(recent.stream().map(TaskResponseDto::fromEntity).toList());
		return dash;
	}

	private Task loadTaskDetailed(Long id) {
		return taskRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
	}

	private static boolean isAdmin(User user) {
		return user.getRole() == Role.ADMIN;
	}

	private static void requireAdmin(User user) {
		if (!isAdmin(user)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Admin only");
		}
	}

	private static boolean isAssignee(User user, Task task) {
		return task.getAssignedTo() != null && task.getAssignedTo().getUser() != null
				&& task.getAssignedTo().getUser().getId().equals(user.getId());
	}

	private static void assertCanRead(User user, Task task) {
		if (isAdmin(user) || isAssignee(user, task)) {
			return;
		}
		throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not allowed to access this task");
	}

	private static Task.Status parseStatus(String raw) {
		try {
			return Task.Status.valueOf(raw.trim().toUpperCase());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status: " + raw);
		}
	}

	private void applyUserPatch(Task task, TaskPatchDto patch, User currentUser) {
		if (!isAssignee(currentUser, task)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only the assignee can update this task");
		}
		if (patch.getTitle() != null || patch.getDescription() != null || patch.getAssignedToEmployeeId() != null
				|| patch.getEvaluatorEmployeeId() != null || patch.getDueDate() != null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You may only update status and completion note");
		}
		if (patch.getStatus() != null) {
			Task.Status next = parseStatus(patch.getStatus());
			validateUserTransition(task.getStatus(), next);
			applyStatusChange(task, next, currentUser, patch.getCompletionNote());
		} else if (patch.getCompletionNote() != null) {
			task.setCompletionNote(patch.getCompletionNote());
		}
	}

	private static void validateUserTransition(Task.Status from, Task.Status to) {
		if (from == to) {
			return;
		}
		if (to == Task.Status.REJECTED) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot reject this task");
		}
		if (to == Task.Status.IN_PROGRESS) {
			if (from != Task.Status.PENDING && from != Task.Status.OVERDUE) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status transition to IN_PROGRESS");
			}
			return;
		}
		if (to == Task.Status.COMPLETED) {
			if (!OPEN_STATUSES.contains(from)) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status transition to COMPLETED");
			}
			return;
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Users may only set IN_PROGRESS or COMPLETED");
	}

	private void applyAdminPatch(Task task, TaskPatchDto patch, User currentUser) {
		if (patch.getTitle() != null) {
			task.setTitle(patch.getTitle());
		}
		if (patch.getDescription() != null) {
			task.setDescription(patch.getDescription());
		}
		if (patch.getDueDate() != null) {
			task.setDueDate(patch.getDueDate());
		}
		if (patch.getAssignedToEmployeeId() != null) {
			Employee assignee = employeeRepository.findById(patch.getAssignedToEmployeeId())
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "assignee not found"));
			task.setAssignedTo(assignee);
		}
		if (patch.getEvaluatorEmployeeId() != null) {
			Employee ev = employeeRepository.findById(patch.getEvaluatorEmployeeId())
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "evaluator not found"));
			task.setEvaluator(ev);
		}
		if (patch.getStatus() != null) {
			Task.Status next = parseStatus(patch.getStatus());
			applyStatusChange(task, next, currentUser, patch.getCompletionNote());
		} else if (patch.getCompletionNote() != null) {
			task.setCompletionNote(patch.getCompletionNote());
		}
	}

	private static void applyStatusChange(Task task, Task.Status next, User actor, String completionNote) {
		Task.Status prev = task.getStatus();
		if (prev == next) {
			if (completionNote != null) {
				task.setCompletionNote(completionNote);
			}
			return;
		}
		if (OPEN_STATUSES.contains(next)) {
			task.setClosedAt(null);
			task.setClosedBy(null);
		}
		if (CLOSED_STATUSES.contains(next)) {
			task.setClosedAt(LocalDateTime.now());
			task.setClosedBy(actor);
		}
		if (completionNote != null) {
			task.setCompletionNote(completionNote);
		}
		task.setStatus(next);
		if (OPEN_STATUSES.contains(next) && CLOSED_STATUSES.contains(prev)) {
			task.setCompletionNote(null);
		}
	}
}
