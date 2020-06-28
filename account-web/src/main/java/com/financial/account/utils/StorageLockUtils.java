package com.financial.account.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StorageLockUtils {
    public static <V> V applyReadAction(ReadWriteLock readWriteLock, Supplier<V> action) {
        Lock lock = readWriteLock.readLock();
        try {
            lock.lock();
            return action.get();
        } finally {
            lock.unlock();
        }
    }

    public static <V> V applyWriteAction(ReadWriteLock readWriteLock, Supplier<V> action) {
        Lock lock = readWriteLock.writeLock();
        try {
            lock.lock();
            return action.get();
        } finally {
            lock.unlock();
        }
    }
}
