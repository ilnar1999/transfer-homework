package com.example;

import com.example.runnable.TransactingRunnable;
import com.example.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application {
    private static final int TRANSACTION_THREAD_COUNT = 20;

    private static final Logger logger = LoggerFactory.getLogger("file-logger");

    public static void main(String[] args) throws InterruptedException {
        ExecutorService transactionExecutorService = Executors.newFixedThreadPool(TRANSACTION_THREAD_COUNT);
        AccountService accountService = new AccountService();

        logger.info("initial count of money: {} total: {}", accountService.getAllBalances(), accountService.getTotalBalance());

        for (int i = 0; i < TRANSACTION_THREAD_COUNT; i++) {
            transactionExecutorService.submit(new TransactingRunnable(accountService));
        }

        Thread.sleep(1000);
        logger.info("final count of money: {} total: {}", accountService.getAllBalances(), accountService.getTotalBalance());
    }
}
