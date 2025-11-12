package com.my.entities;

public class AuthenticationResponse {
    private String token;
    private Role role;
    private String name;

    public AuthenticationResponse(String token, Role role2,String name) {
        System.out.println("Generating Authentication Response...");
        this.token = token;
        this.role = role2;
        this.name=name;
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
}
