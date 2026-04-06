package com.my.dto;

import java.time.LocalDate;

import com.my.entities.Role;

public class EmployeeCreateDto {

	/**
	 * Link this existing user (must not already have an employee row). If set,
	 * {@link #username} / new-user fields are ignored.
	 */
	private Long userId;

	/** Required when {@code userId} is null: login name for a new account. */
	private String username;

	private String firstName;
	private String lastName;

	/** Plain text; defaults to {@code admin} when creating a new user. Ignored when linking by {@code userId}. */
	private String password;

	/** Defaults to {@link Role#EMPLOYEE} for newly created users. */
	private Role role;

	private Integer departmentId;
	private Integer designationId;
	private LocalDate hireDate;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public Integer getDesignationId() {
		return designationId;
	}

	public void setDesignationId(Integer designationId) {
		this.designationId = designationId;
	}

	public LocalDate getHireDate() {
		return hireDate;
	}

	public void setHireDate(LocalDate hireDate) {
		this.hireDate = hireDate;
	}
}
