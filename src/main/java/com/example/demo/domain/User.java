package com.example.demo.domain;

import com.example.demo.dto.Role;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "auth_user" )
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String token;
    @Enumerated(value = EnumType.STRING)
    private Role role;

}
