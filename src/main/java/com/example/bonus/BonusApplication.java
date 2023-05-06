package com.example.bonus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BonusApplication {

    public static void main(String[] args) {
        SpringApplication.run(BonusApplication.class, args);
    }
}
