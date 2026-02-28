package com.bancose.digital_bank.config;

import com.bancose.digital_bank.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataLoader {

    private final UserService userService;

    @Bean
    CommandLineRunner run() {
        return args -> {
            userService.register(
                    "Yago",
                    "yago@email.com",
                    "123456"
            );
        };
    }
}