package org.example.authservice.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    // No @Autowired field

    @Bean
    public CommandLineRunner testConnection(DataSource dataSource) {
        return args -> {
            System.out.println("Testing database connection...");
            try (var connection = dataSource.getConnection()) {
                System.out.println("Database connection successful!");
                System.out.println("Connected to: " + connection.getMetaData().getURL());
                System.out.println("Database product: " + connection.getMetaData().getDatabaseProductName());
                System.out.println("Database version: " + connection.getMetaData().getDatabaseProductVersion());
            } catch (Exception e) {
                System.err.println("Database connection failed:");
                e.printStackTrace();
            }
        };
    }
}