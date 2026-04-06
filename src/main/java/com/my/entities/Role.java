package com.my.entities;

/**
 * {@link #USER} exists only for legacy {@code user_master.role} values from older releases.
 * It is treated as {@link #EMPLOYEE} for JWT claims and Spring Security authorities.
 */
public enum Role {
	ADMIN,
	MANAGER,
	EMPLOYEE,
	/** @deprecated Persisted historical value only — migrate DB to {@code EMPLOYEE}. */
	USER;

	/** Spring {@code ROLE_*} suffix and JWT {@code role} claim (never exposes {@link #USER}). */
	public String toAuthorityAndClaimName() {
		return this == USER ? EMPLOYEE.name() : name();
	}

	public static Role normalizeForPersist(Role r) {
		if (r == null) {
			return EMPLOYEE;
		}
		return r == USER ? EMPLOYEE : r;
	}
}
