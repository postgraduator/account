package com.financial.account.controller;

import com.financial.account.model.Transaction;
import com.financial.account.storage.TransactionStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/account/{accountId}/transaction")
public class TransactionController {
    private final TransactionStorage transactionStorage;

    @PostMapping("/credit")
    public Transaction createCreditTransaction(@PathVariable UUID accountId, @RequestParam BigDecimal amount) {
        return transactionStorage.credit(accountId, amount);
    }

    @PostMapping("/debit")
    public Transaction createDebitTransaction(@PathVariable UUID accountId, @RequestParam BigDecimal amount) {
        return transactionStorage.debit(accountId, amount);
    }

    @GetMapping
    public List<Transaction> getAll() {
        return transactionStorage.getAll();
    }
}
