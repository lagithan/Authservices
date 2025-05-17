package org.example.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class StoreCreateRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Store name is required")
    @Size(min = 3, max = 100, message = "Store name must be between 3 and 100 characters")
    private String storeName;

    @Size(max = 500, message = "Store description must be less than 500 characters")
    private String storeDescription;

    // Business information
    @NotBlank(message = "Business type is required")
    private String storeType;

    @NotBlank(message = "Business category is required")
    private String businessCategory;

    @NotBlank(message = "Contact phone is required")
    @Pattern(regexp = "^\\+?[0-9]{8,15}$", message = "Phone number should be valid")
    private String contactPhone;

    @Email(message = "Business email should be valid")
    private String businessEmail;

    @NotBlank(message = "Business address cannot be empty")
    private String businessAddress;

    private String storeLogoUrl;

    // Getter methods
    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getStoreDescription() {
        return storeDescription;
    }

    public String getStoreType() {
        return storeType;
    }

    public String getBusinessCategory() {
        return businessCategory;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public String getBusinessEmail() {
        return businessEmail;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public String getStoreLogoUrl() {
        return storeLogoUrl;
    }

    // Setter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setStoreDescription(String storeDescription) {
        this.storeDescription = storeDescription;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public void setBusinessCategory(String businessCategory) {
        this.businessCategory = businessCategory;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public void setBusinessEmail(String businessEmail) {
        this.businessEmail = businessEmail;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public void setStoreLogoUrl(String storeLogoUrl) {
        this.storeLogoUrl = storeLogoUrl;
    }
}