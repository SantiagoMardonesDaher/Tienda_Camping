package com.uade.tpo.demo.controllers.auth;

import lombok.Data;

@Data
public class UserRequest {
    private String email;
    private String name;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
}