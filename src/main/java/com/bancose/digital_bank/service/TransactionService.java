package com.bancose.digital_bank.service;

import com.bancose.digital_bank.model.Account;
import com.bancose.digital_bank.model.Transaction;
import com.bancose.digital_bank.model.Transaction.TransactionType;
import com.bancose.digital_bank.repository.AccountRepository;
import com.bancose.digital_bank.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public Transaction deposit(Account account, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Valor do depósito deve ser maior que zero");
        }

        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);

        Transaction transaction = Transaction.builder()
                .type(TransactionType.DEPOSIT)
                .amount(amount)
                .destinationAccount(account)
                .createdAt(LocalDateTime.now())
                .build();

        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction withdraw(Account account, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Valor do saque deve ser maior que zero");
        }

        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }

        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);

        Transaction transaction = Transaction.builder()
                .type(TransactionType.WITHDRAW)
                .amount(amount)
                .sourceAccount(account)
                .createdAt(LocalDateTime.now())
                .build();

        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction transfer(Account source, Account destination, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Valor da transferência deve ser maior que zero");
        }

        if (source.getId().equals(destination.getId())) {
            throw new RuntimeException("Não é possível transferir para a mesma conta");
        }

        if (source.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }

        source.setBalance(source.getBalance().subtract(amount));
        destination.setBalance(destination.getBalance().add(amount));

        accountRepository.save(source);
        accountRepository.save(destination);

        Transaction transaction = Transaction.builder()
                .type(TransactionType.TRANSFER)
                .amount(amount)
                .sourceAccount(source)
                .destinationAccount(destination)
                .createdAt(LocalDateTime.now())
                .build();

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getStatement(Account account) {
        return transactionRepository
                .findBySourceAccountOrDestinationAccountOrderByCreatedAtDesc(account, account);
    }
}