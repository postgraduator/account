package com.financial.account.storage;

import com.financial.account.model.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface TransactionStorage {
    Transaction debit(UUID accountId, BigDecimal amount);
    Transaction credit(UUID accountId, BigDecimal amount);

    List<Transaction> getAll();
}
