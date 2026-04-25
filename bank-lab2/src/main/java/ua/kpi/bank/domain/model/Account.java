package ua.kpi.bank.domain.model;

import ua.kpi.bank.domain.exception.DomainError;

import java.math.BigDecimal;
import java.util.UUID;

public class Account {

    private final UUID id;
    private BigDecimal balance;
    private final String currency;

    public Account(UUID id, BigDecimal balance, String currency) {
        if (balance.compareTo(BigDecimal.ZERO) < 0)
            throw new DomainError("Balance cannot be negative");

        if (currency == null || currency.isBlank())
            throw new DomainError("Currency is required");

        this.id = id;
        this.balance = balance;
        this.currency = currency;
    }

    public void deposit(BigDecimal amount) {
        validateAmount(amount);
        balance = balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        validateAmount(amount);

        if (balance.compareTo(amount) < 0)
            throw new DomainError("Insufficient funds");

        balance = balance.subtract(amount);
    }

    private void validateAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new DomainError("Invalid amount");
    }

    public UUID getId() { return id; }
    public BigDecimal getBalance() { return balance; }
    public String getCurrency() { return currency; }
}