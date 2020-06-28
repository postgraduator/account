package com.financial.account.storage;

import java.util.concurrent.locks.ReadWriteLock;

public interface StorageLockProvider {
    ReadWriteLock getAccountStorageLock();

    ReadWriteLock getTransactionLock();
}
