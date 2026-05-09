package ua.kpi.bank.core.domain.model;

import ua.kpi.bank.core.domain.exception.DomainError;

import java.math.BigDecimal;

public class Money {

    private final BigDecimal amount;
    private final String currency;

    public Money(BigDecimal amount, String currency) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new DomainError("Invalid amount");
        }

        if (currency == null || currency.isBlank()) {
            throw new DomainError("Invalid currency");
        }

        this.amount = amount;
        this.currency = currency.toUpperCase();
    }

    public Money add(Money other) {
        checkCurrency(other);
        return new Money(amount.add(other.amount), currency);
    }

    public Money subtract(Money other) {
        checkCurrency(other);

        if (amount.compareTo(other.amount) < 0) {
            throw new DomainError("Insufficient funds");
        }

        return new Money(amount.subtract(other.amount), currency);
    }

    private void checkCurrency(Money other) {
        if (!currency.equals(other.currency)) {
            throw new DomainError("Currency mismatch");
        }
    }

    public BigDecimal getAmount() { return amount; }
    public String getCurrency() { return currency; }
}