package org.example.authservice.entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "store_profiles")
public class StoreProfile {
    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @Column(unique = true)
    private String storeName;
    private String storeDescription;
    private String businessAddress;
    private String contactPhone;
    private String businessEmail;
    private LocalDateTime storeCreatedAt = LocalDateTime.now();
    private String storeLogoUrl;
    private String businessCategory;
    private boolean storeVerified = false;
    private String storeType;

    // Getter methods
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getStoreDescription() {
        return storeDescription;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public String getBusinessEmail() {
        return businessEmail;
    }

    public LocalDateTime getStoreCreatedAt() {
        return storeCreatedAt;
    }

    public String getStoreLogoUrl() {
        return storeLogoUrl;
    }

    public String getBusinessCategory() {
        return businessCategory;
    }

    public boolean isStoreVerified() {
        return storeVerified;
    }

    public String getStoreType() {
        return storeType;
    }

    // Setter methods
    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setStoreDescription(String storeDescription) {
        this.storeDescription = storeDescription;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public void setBusinessEmail(String businessEmail) {
        this.businessEmail = businessEmail;
    }

    public void setStoreCreatedAt(LocalDateTime storeCreatedAt) {
        this.storeCreatedAt = storeCreatedAt;
    }

    public void setStoreLogoUrl(String storeLogoUrl) {
        this.storeLogoUrl = storeLogoUrl;
    }

    public void setBusinessCategory(String businessCategory) {
        this.businessCategory = businessCategory;
    }

    public void setStoreVerified(boolean storeVerified) {
        this.storeVerified = storeVerified;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }
}