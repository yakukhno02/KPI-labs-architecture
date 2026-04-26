package ua.kpi.bank.domain.model;

import ua.kpi.bank.domain.exception.DomainError;

import java.util.UUID;

public class Account {

    private final UUID id;
    private Money balance;

    public Account(UUID id, Money balance) {
        if (balance == null) {
            throw new DomainError("Balance required");
        }

        this.id = id;
        this.balance = balance;
    }

    public void deposit(Money money) {
        balance = balance.add(money);
    }

    public void withdraw(Money money) {
        balance = balance.subtract(money);
    }

    public UUID getId() { return id; }
    public Money getBalance() { return balance; }
}