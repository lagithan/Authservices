package org.example.authservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class StoreSignupResponse {
    private boolean isemailsent;
    private String email;

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public boolean isemailsent() {
        return isemailsent;
    }
    public void setemailsent(boolean isemailsent) {
        this.isemailsent = isemailsent;
    }

}
