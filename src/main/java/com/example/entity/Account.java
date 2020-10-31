package com.example.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Data
public class Account implements Serializable {
    private final Long id;
    private String customerName;
    private int balance;
    private Lock lock = new ReentrantLock();

    public Account(Long id, String customerName, int balance) {
        this.id = id;
        this.customerName = customerName;
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}