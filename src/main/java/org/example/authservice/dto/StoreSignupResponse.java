package org.example.authservice.dto;


import lombok.*;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Getter
@Setter
public class StoreSignupResponse {
    private boolean isemailsent;
    private String email;
    private String message;

    public void setemailsent(boolean isemailsent) {
        this.isemailsent = isemailsent;
    }

}
