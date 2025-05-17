package org.example.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories(basePackages = "org.example.authservice.repository")
public class AuthservicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthservicesApplication.class, args);
    }

}
