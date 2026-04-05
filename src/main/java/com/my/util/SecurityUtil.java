package com.my.util;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import com.my.entities.User;

public final class SecurityUtil {

	private SecurityUtil() {
	}

	public static User requireCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null || !auth.isAuthenticated() || !(auth.getPrincipal() instanceof User)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authenticated");
		}
		return (User) auth.getPrincipal();
	}
}
