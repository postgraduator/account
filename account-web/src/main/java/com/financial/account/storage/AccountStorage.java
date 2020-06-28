package com.financial.account.storage;

import com.financial.account.model.Account;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;

public interface AccountStorage {
    Account createAccount(BigDecimal value);
    Account getById(UUID id);
    Collection<Account> getAll();
}
