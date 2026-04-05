package com.my.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.my.dto.TaskCreateDto;
import com.my.dto.TaskPatchDto;
import com.my.dto.TaskResponseDto;
import com.my.dto.TaskUpdateDto;
import com.my.dto.UserDashboardDto;
import com.my.entities.Task;
import com.my.entities.User;

public interface TaskService {

	Task createTask(User currentUser, TaskCreateDto dto);

	List<TaskResponseDto> getAllTasksForAdmin(User currentUser);

	Page<TaskResponseDto> listTasks(User currentUser, String scope, Pageable pageable);

	TaskResponseDto getTaskById(User currentUser, Long id);

	TaskResponseDto patchTask(User currentUser, Long id, TaskPatchDto patch);

	TaskResponseDto updateTask(User currentUser, Long id, TaskUpdateDto dto);

	void deleteTask(User currentUser, Long id);

	UserDashboardDto getMyDashboard(User currentUser);
}
