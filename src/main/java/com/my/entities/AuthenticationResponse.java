package com.my.entities;

public class AuthenticationResponse {
    private String token;
    private Role role;

    public AuthenticationResponse(String token, Role role2) {
        System.out.println("Generating Authentication Response...");
        this.token = token;
        this.role = role2;
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
}
