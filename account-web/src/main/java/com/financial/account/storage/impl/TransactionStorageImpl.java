package com.financial.account.storage.impl;

import com.financial.account.model.Account;
import com.financial.account.model.Transaction;
import com.financial.account.model.type.TransactionType;
import com.financial.account.storage.AccountStorage;
import com.financial.account.storage.StorageLockProvider;
import com.financial.account.storage.TransactionStorage;
import com.financial.account.storage.exception.CreditTranscationError;
import com.financial.account.storage.exception.TransactionError;
import com.financial.account.utils.StorageLockUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionStorageImpl implements TransactionStorage {
    private final StorageLockProvider lockProvider;
    private final AccountStorage accountStorage;
    private final Map<UUID, Transaction> transactionMap = new HashMap<>();

    @Override
    public Transaction debit(UUID accountId, BigDecimal amount) {
        Supplier<Transaction> debitCreator = () -> {
            validateAmount(amount);
            Account account = accountStorage.getById(accountId);
            account.setValue(account.getValue().add(amount));
            Transaction transaction = Transaction.builder()
                    .id(UUID.randomUUID())
                    .type(TransactionType.DEBIT)
                    .createdAt(LocalDate.now())
                    .total(account.getValue())
                    .amount(amount)
                    .build();
            transactionMap.put(transaction.getId(), transaction);
            return transaction;
        };
        return StorageLockUtils.applyWriteAction(lockProvider.getTransactionLock(), debitCreator);
    }

    @Override
    public Transaction credit(UUID accountId, BigDecimal amount) {
        Supplier<Transaction> creditCreator = () -> {
            validateAmount(amount);
            Account account = accountStorage.getById(accountId);
            if (amount.compareTo(account.getValue()) > 0) {
                throw new CreditTranscationError();
            }
            account.setValue(account.getValue().subtract(amount));
            Transaction transaction = Transaction.builder()
                    .id(UUID.randomUUID())
                    .type(TransactionType.CREDIT)
                    .createdAt(LocalDate.now())
                    .total(account.getValue())
                    .amount(amount)
                    .build();
            transactionMap.put(transaction.getId(), transaction);
            return transaction;
        };
        return StorageLockUtils.applyWriteAction(lockProvider.getTransactionLock(), creditCreator);
    }

    @Override
    public List<Transaction> getAll() {
        Supplier<List<Transaction>> all = () -> transactionMap.values().stream()
                .sorted(Comparator.comparing(Transaction::getCreatedAt))
                .collect(Collectors.toList());
        return StorageLockUtils.applyReadAction(lockProvider.getTransactionLock(), all);
    }

    private void validateAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new TransactionError("The requested amount has to be more than 0");
        }
    }
}
