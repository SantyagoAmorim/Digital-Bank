package com.bancose.digital_bank.dto;

import java.math.BigDecimal;

public record AccountDTO(
        Long id,
        String ownerName,
        String ownerEmail,
        BigDecimal balance
) {}