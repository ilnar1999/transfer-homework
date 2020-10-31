package com.example.runnable;

import com.example.entity.Account;
import com.example.service.AccountService;
import com.example.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class TransactingRunnable implements Runnable {
    private static final int TOTAL_COUNT_OF_ACCOUNTS = AccountService.getTotalCountOfAccounts();
    private static final int TOTAL_COUNT_OF_TRANSACTIONS = 1_000;

    private static final AtomicInteger currentCountOfTransactions = new AtomicInteger(0);

    private final Random random = new Random();
    private final TransactionService transactionService = new TransactionService();
    private final Logger logger = LoggerFactory.getLogger("file-logger");

    private final AccountService accountService;

    public TransactingRunnable(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void run() {
        long fromId;
        long toId;
        long amount;
        Account fromAccount;
        Account toAccount;
        Account firstLock;
        Account secondLock;

        while (currentCountOfTransactions.getAndAdd(1) < TOTAL_COUNT_OF_TRANSACTIONS) {
            do {
                fromId = this.random.nextInt(TOTAL_COUNT_OF_ACCOUNTS) + 1L;
                toId = this.random.nextInt(TOTAL_COUNT_OF_ACCOUNTS) + 1L;
            } while (fromId == toId);

            fromAccount = this.accountService.getById(fromId);
            toAccount = this.accountService.getById(toId);

            if (fromAccount.getId() < toAccount.getId()) {
                firstLock = fromAccount;
                secondLock = toAccount;
            } else {
                firstLock = toAccount;
                secondLock = fromAccount;
            }

            firstLock.getLock().lock();
            secondLock.getLock().lock();

            amount = this.random.nextInt(fromAccount.getBalance());

            this.transactionService.transact(
                    fromAccount,
                    toAccount,
                    amount
            );

            secondLock.getLock().unlock();
            firstLock.getLock().unlock();
        }
    }
}
