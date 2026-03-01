package com.bancose.digital_bank.repository;

import com.bancose.digital_bank.model.Account;
import com.bancose.digital_bank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUser(User user);
}