package com.example.noteapp.note.dto.request;

/*
in Spring Boot, the DTO package contains Data Transfer Objects (DTOs).
These are Java classes whose only job is to transfer data
between different layers of your application, such as between the client and your REST API.
 */

public class LoginRequest {
    private String email;

    private String password;

    public String getEmail() {
        return email;
    }

    public LoginRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LoginRequest setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
