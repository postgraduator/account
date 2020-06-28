package com.financial.account.model;

import com.financial.account.model.type.TransactionType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class Transaction {
    private final UUID id;
    private final TransactionType type;
    private final BigDecimal amount;
    private final LocalDate createdAt;
    private final BigDecimal total;
}
