package com.financial.account.storage.impl;

import com.financial.account.model.Account;
import com.financial.account.storage.AccountStorage;
import com.financial.account.storage.StorageLockProvider;
import com.financial.account.storage.exception.EntityNotFoundException;
import com.financial.account.utils.StorageLockUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class AccountStorageImpl implements AccountStorage {
    private final StorageLockProvider lockProvider;
    private final Map<UUID, Account> accountMap = new HashMap<>();
    @Override
    public Account createAccount(BigDecimal value) {
        Supplier<Account> creator = () -> {
            if (accountMap.size() > 0) {
                accountMap.clear();
            }
            Account account = createAccountFromValue(value);
            accountMap.put(account.getId(), account);
            return account;
        };
        return StorageLockUtils.applyWriteAction(lockProvider.getAccountStorageLock(), creator);
    }

    @Override
    public Account getById(UUID id) {
        Supplier<Account> getter = () -> {
            Account account = accountMap.get(id);
            if (account == null) {
                throw new EntityNotFoundException(String.format("Account with the given id %s does not exist", id.toString()));
            }
            return account;
        };
        return StorageLockUtils.applyReadAction(lockProvider.getAccountStorageLock(), getter);
    }

    @Override
    public Collection<Account> getAll() {
        return accountMap.values();
    }

    private Account createAccountFromValue(BigDecimal value) {
        Account account = new Account();
        account.setValue(value);
        account.setId(UUID.randomUUID());
        return account;
    }
}
