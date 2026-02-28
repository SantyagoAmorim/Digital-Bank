package com.bancose.digital_bank.service;

import com.bancose.digital_bank.model.User;
import com.bancose.digital_bank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(String name, String email, String rawPassword) {

        userRepository.findByEmail(email).ifPresent(user -> {
            throw new RuntimeException("Email já cadastrado");
        });

        User user = User.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(rawPassword))
                .CreatedAt(LocalDateTime.now())
                .build();

        return userRepository.save(user);
    }
}