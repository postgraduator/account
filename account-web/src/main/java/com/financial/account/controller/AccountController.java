package com.financial.account.controller;

import com.financial.account.model.Account;
import com.financial.account.storage.AccountStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountStorage accountStorage;

    @PostMapping
    public Account createAccount(@RequestParam BigDecimal value) {
        return accountStorage.createAccount(value);
    }

    @GetMapping("/{accountId}")
    public Account getAccount(@PathVariable UUID accountId) {
        return accountStorage.getById(accountId);
    }

    @GetMapping
    public Collection<Account> getAll() {
        return accountStorage.getAll();
    }
}
