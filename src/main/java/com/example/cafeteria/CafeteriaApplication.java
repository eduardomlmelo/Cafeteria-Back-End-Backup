package com.example.cafeteria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.database.Entities")
@EnableJpaRepositories("com.database.Entities.Interfaces")
public class CafeteriaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CafeteriaApplication.class, args);
    }
}