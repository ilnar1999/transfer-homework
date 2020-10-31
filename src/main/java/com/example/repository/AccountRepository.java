package com.example.repository;

import com.example.entity.Account;
import com.example.service.ReadService;
import com.example.service.WriteService;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class AccountRepository {
    private final WriteService writeService = new WriteService();
    private final ReadService readService = new ReadService();
    private final FileRepository fileRepository = new FileRepository();
    private final Lock locker = new ReentrantLock();

    private final Set<Account> accounts = new HashSet<>();

    public void create(Account account) {
        this.fileRepository.create(account.getId());
        this.update(account);
    }

    public void setAccounts() {
        Account account;
        for (File file : this.fileRepository.getAll()) {
            account = (Account) this.readService.readObjectFromFile(file);
            this.accounts.add(account);
        }
    }

    public long getTotalBalance() {
        this.locker.lock();
        long totalBalance = this.accounts.stream().mapToInt(Account::getBalance).sum();
        this.locker.unlock();
        return totalBalance;
    }

    public List<Integer> getAllBalances() {
        return this.accounts.stream()
                .map(Account::getBalance)
                .collect(Collectors.toList());
    }

    public Account getById(Long id) {
        this.locker.lock();
        Account account;

        account = this.accounts.stream()
                .filter(account1 -> account1.getId().equals(id))
                .findFirst()
                .orElse(null);

        this.locker.unlock();
        return account;
    }

    public void update(Account account) {
        File file = this.fileRepository.getById(account.getId());
        this.writeService.writeObjectToFile(account, file);
    }

    public void deleteAll() {
        this.fileRepository.deleteAll();
    }
}
