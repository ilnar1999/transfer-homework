package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

import java.util.List;
import java.util.Random;


public class AccountService {
    private static final int TOTAL_COUNT_OF_ACCOUNTS = 10;

    private final AccountRepository accountRepository = new AccountRepository();
    private final Random random = new Random();

    public static int getTotalCountOfAccounts() {
        return TOTAL_COUNT_OF_ACCOUNTS;
    }

    public AccountService() {
        this.init();
    }

    public void init() {
        Account account;
        int initialBalance;
        this.accountRepository.deleteAll();
        for (long id = 1; id <= TOTAL_COUNT_OF_ACCOUNTS; id++) {
            initialBalance = this.random.nextInt(1000);
            account = new Account(id, "Customer", initialBalance);
            this.accountRepository.create(account);
        }
        this.accountRepository.setAccounts();
    }

    public long getTotalBalance() {
        return this.accountRepository.getTotalBalance();
    }

    public List<Integer> getAllBalances() {
        return this.accountRepository.getAllBalances();
    }

    public Account getById(Long id) {
        return this.accountRepository.getById(id);
    }
}
