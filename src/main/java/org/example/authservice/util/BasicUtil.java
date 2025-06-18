package org.example.authservice.util;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BasicUtil {


    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    public String generateId(){
        return UUID.randomUUID().toString();
    }


}
