package org.example.authservice.dto;

import org.example.authservice.entity.User;

public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String profilePicture;
    private String role;

    public UserDto(Long id, String name, String email, String profilePicture, User.UserRole role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.profilePicture = profilePicture;
        this.role = role.toString();
    }

    public UserDto() {

    }

    // Getter methods
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getRole() {
        return role;
    }

    // Setter methods
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setRole(String role) {
        this.role = role;
    }
}