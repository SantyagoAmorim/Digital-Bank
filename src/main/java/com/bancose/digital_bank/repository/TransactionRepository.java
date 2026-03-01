package com.bancose.digital_bank.repository;

import com.bancose.digital_bank.model.Account;
import com.bancose.digital_bank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySourceAccountOrDestinationAccountOrderByCreatedAtDesc(
            Account source, Account destination
    );
}