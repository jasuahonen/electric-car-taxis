package com.example.charging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ChargingApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChargingApplication.class, args);
    }
}
