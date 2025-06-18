package org.example.authservice.dto;

import org.example.authservice.entity.User;

public class TemprorayUser {

    private String name;
    private String email;
    private String password;
    private User.UserRole role = User.UserRole.store_owner;
    public TemprorayUser() {}
    public TemprorayUser(int id, String name, String email, String password) {}


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public User.UserRole getRole() {
        return role;
    }
    public void setRole(User.UserRole role) {
        this.role = role;
    }
    }
