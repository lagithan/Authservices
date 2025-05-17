package org.example.authservice.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Builder
@Data
public class User {

    // Auth Provider enum
    public enum AuthProvider {
        LOCAL,
        GOOGLE
    }

    // User Role enum
    public enum UserRole {
        customer,
        store_owner,
        admin
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    private String profilePicture;

    // Fixed: Removed @Enumerated annotation as 'active' is a boolean, not an enum
    @Column(nullable = false)
    private boolean active = true;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    // Single role per user
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // No-args constructor (from @NoArgsConstructor)
    public User() {
    }

    // All-args constructor (from @AllArgsConstructor)
    public User(Long id, String email, String password, String name, String profilePicture,
                boolean active, AuthProvider provider, UserRole role,
                LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.profilePicture = profilePicture;
        this.active = active;
        this.provider = provider;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Convenience constructors (kept from original)
    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.provider = AuthProvider.LOCAL;
        this.role = UserRole.customer;
    }

    //Constructor for role based user
    public User(String email, String password, String name, UserRole role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.provider = AuthProvider.LOCAL;
        this.role = role;
    }

    //Constructor for the oauth_based user
    public User(String email, String name, AuthProvider provider, String profilePicture) {
        this.email = email;
        this.name = name;
        this.provider = provider;
        this.role = UserRole.customer;
        this.profilePicture = profilePicture;
    }

    // Utility methods
    public boolean hasRole(UserRole role) {
        return this.role == role;
    }

    // Getter methods
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public boolean isActive() {
        return active;
    }

    public AuthProvider getProvider() {
        return provider;
    }

    public UserRole getRole() {
        return role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Setter methods
    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setProvider(AuthProvider provider) {
        this.provider = provider;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}