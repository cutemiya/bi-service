package com.example.newspot.models.Db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "accounts")
public class User {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name="email")
    private String email;

    @Column(name="username")
    private String username;

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
