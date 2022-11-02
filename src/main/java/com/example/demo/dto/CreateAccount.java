package com.example.demo.dto;

import lombok.Data;

@Data
public class CreateAccount {
    private String firstName;
    private String lastName;
    private Role role;
    private String password;
    private String username;

}
