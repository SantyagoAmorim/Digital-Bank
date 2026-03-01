package com.bancose.digital_bank.service;

import com.bancose.digital_bank.model.Account;
import com.bancose.digital_bank.model.User;
import com.bancose.digital_bank.repository.AccountRepository;
import com.bancose.digital_bank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AccountRepository accountRepository;

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

        userRepository.save(user);

        Account account = Account.builder()
                .user(user)
                .balance(BigDecimal.ZERO)
                .createdAt(LocalDateTime.now())
                .build();

        accountRepository.save(account);

        return user;
    }

    public String login(String email, String rawPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Senha incorreta");
        }

        return jwtService.generateToken(email);
    }
}