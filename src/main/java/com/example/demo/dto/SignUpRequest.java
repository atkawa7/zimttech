package com.example.demo.dto;

import lombok.Data;

@Data
public class SignUpRequest {
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private Role role;

}
