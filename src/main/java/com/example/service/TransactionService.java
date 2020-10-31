package com.example.service;

import com.example.entity.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionService {
    private final Logger logger = LoggerFactory.getLogger("file-logger");
    public void transact(Account from, Account to, long amount) {
//        logger.info("start transaction from account №{} to account №{} to amount of {}", from.getId(), to.getId(), amount);
        long fromBalance;
        long toBalance;

        fromBalance = from.getBalance();
        toBalance = to.getBalance();

        from.setBalance((int) (fromBalance - amount));
        to.setBalance((int) (toBalance + amount));
    }
}
