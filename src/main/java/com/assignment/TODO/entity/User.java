package com.assignment.TODO.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    // Getters and Setters
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public Long getId() {
        return id;
    }

}
