package org.example.authservice.dto;

import org.example.authservice.entity.User;

public class TemprorayUser {

    private String name;
    private String email;
    private String password;
    private User.UserRole role = User.UserRole.store_owner;
    private User.AuthProvider provider = User.AuthProvider.LOCAL;
    public TemprorayUser() {}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public User.UserRole getRole() {
        return this.role;
    }
    public void setRole(User.UserRole role) {
        this.role = role;
    }
    public User.AuthProvider getProvider() {return this.provider;}
    public void setProvider(User.AuthProvider provider) {
        this.provider = provider;
    }
    }
