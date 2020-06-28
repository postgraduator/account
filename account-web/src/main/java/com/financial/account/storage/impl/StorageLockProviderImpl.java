package com.financial.account.storage.impl;

import com.financial.account.storage.StorageLockProvider;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class StorageLockProviderImpl implements StorageLockProvider {
    private final ReadWriteLock transactionLock = new ReentrantReadWriteLock();
    private final ReadWriteLock accountLock = new ReentrantReadWriteLock();
    @Override
    public ReadWriteLock getAccountStorageLock() {
        return accountLock;
    }

    @Override
    public ReadWriteLock getTransactionLock() {
        return transactionLock;
    }
}
