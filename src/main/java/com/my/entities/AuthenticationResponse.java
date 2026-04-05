package com.my.entities;

public class AuthenticationResponse {
    private String token;
    private Role role;
    private String name;
    private Long userId;
    private String username;

    public AuthenticationResponse(String token, Role role2, String name, Long userId, String username) {
        System.out.println("Generating Authentication Response...");
        this.token = token;
        this.role = role2;
        this.name = name;
        this.userId = userId;
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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
}
