package org.example.authservice.dto;

import java.time.LocalDateTime;

public class StoreDto {
    private String storeName;
    private String storeDescription;
    private String businessAddress;
    private String contactPhone;
    private String businessEmail;
    private LocalDateTime storeCreatedAt = LocalDateTime.now();
    private String storeLogoUrl;
    private String businessCategory;
    private String storeType;
    private boolean storeVerified = false;

    // Getter methods
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

    public String getStoreType() {
        return storeType;
    }

    public boolean isStoreVerified() {
        return storeVerified;
    }

    // Setter methods
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

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public void setStoreVerified(boolean storeVerified) {
        this.storeVerified = storeVerified;
    }
}