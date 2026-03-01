package com.bancose.digital_bank.dto;

import com.bancose.digital_bank.model.Transaction.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDTO(
        Long id,
        TransactionType type,
        BigDecimal amount,
        String sourceAccountId,
        String destinationAccountId,
        LocalDateTime createdAt
) {}