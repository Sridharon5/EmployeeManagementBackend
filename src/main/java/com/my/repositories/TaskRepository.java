package com.my.repositories;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.my.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

	@Query("SELECT COUNT(DISTINCT t.id) FROM Task t")
	long countDistinctById();

	@EntityGraph(attributePaths = { "assignedTo", "assignedTo.user", "evaluator", "evaluator.user", "closedBy" })
	Page<Task> findByAssignedTo_Id(Long assignedToId, Pageable pageable);

	@EntityGraph(attributePaths = { "assignedTo", "assignedTo.user", "evaluator", "evaluator.user", "closedBy" })
	Page<Task> findAll(Pageable pageable);

	@Override
	@EntityGraph(attributePaths = { "assignedTo", "assignedTo.user", "evaluator", "evaluator.user", "closedBy" })
	Optional<Task> findById(Long id);

	long countByAssignedTo_IdAndStatusIn(Long employeeId, Collection<Task.Status> statuses);

	@Query("SELECT COUNT(t) FROM Task t WHERE t.assignedTo.id = :empId AND t.status IN :openStatuses AND t.dueDate >= :from AND t.dueDate <= :to")
	long countOpenTasksDueBetween(@Param("empId") Long empId,
			@Param("openStatuses") Collection<Task.Status> openStatuses, @Param("from") LocalDate from,
			@Param("to") LocalDate to);

	@EntityGraph(attributePaths = { "assignedTo", "assignedTo.user", "evaluator", "evaluator.user", "closedBy" })
	List<Task> findTop5ByAssignedTo_IdAndStatusInAndClosedAtIsNotNullOrderByClosedAtDesc(Long employeeId,
			Collection<Task.Status> statuses);
}
