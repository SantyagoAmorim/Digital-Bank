package com.bancose.digital_bank.controller;

import com.bancose.digital_bank.dto.TransactionDTO;
import com.bancose.digital_bank.model.Account;
import com.bancose.digital_bank.model.Transaction;
import com.bancose.digital_bank.model.User;
import com.bancose.digital_bank.repository.AccountRepository;
import com.bancose.digital_bank.repository.UserRepository;
import com.bancose.digital_bank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @GetMapping("/balance/{email}")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable String email) {
        Account account = getAccountByEmail(email);
        return ResponseEntity.ok(account.getBalance());
    }

    @PostMapping("/deposit/{email}")
    public ResponseEntity<String> deposit(@PathVariable String email,
                                          @RequestBody AmountRequest request) {
        Account account = getAccountByEmail(email);
        transactionService.deposit(account, request.amount());
        return ResponseEntity.ok("Depósito realizado! Novo saldo: " + account.getBalance().add(request.amount()));
    }

    @PostMapping("/withdraw/{email}")
    public ResponseEntity<String> withdraw(@PathVariable String email,
                                           @RequestBody AmountRequest request) {
        Account account = getAccountByEmail(email);
        transactionService.withdraw(account, request.amount());
        return ResponseEntity.ok("Saque realizado!");
    }

    @PostMapping("/transfer/{email}")
    public ResponseEntity<String> transfer(@PathVariable String email,
                                           @RequestBody TransferRequest request) {
        Account source = getAccountByEmail(email);
        Account destination = getAccountByEmail(request.destinationEmail());
        transactionService.transfer(source, destination, request.amount());
        return ResponseEntity.ok("Transferência realizada com sucesso!");
    }

    @GetMapping("/statement/{email}")
    public ResponseEntity<List<TransactionDTO>> getStatement(@PathVariable String email) {
        Account account = getAccountByEmail(email);
        List<TransactionDTO> statement = transactionService.getStatement(account)
                .stream()
                .map(transactionService::toDTO)
                .toList();
        return ResponseEntity.ok(statement);
    }

    private Account getAccountByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return accountRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
    }

    public record AmountRequest(BigDecimal amount) {}
    public record TransferRequest(String destinationEmail, BigDecimal amount) {}
}