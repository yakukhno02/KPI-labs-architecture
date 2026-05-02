package ua.kpi.bank.domain.model;

import org.junit.jupiter.api.Test;
import ua.kpi.bank.domain.exception.DomainError;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void shouldNotAllowWithdrawMoreThanBalance() {
        Account acc = new Account(
                UUID.randomUUID(),
                new Money(new BigDecimal("100"), "USD")
        );

        assertThrows(DomainError.class, () ->
                acc.withdraw(new Money(new BigDecimal("200"), "USD"))
        );
    }

    @Test
    void shouldDepositMoney() {
        Account acc = new Account(
                UUID.randomUUID(),
                new Money(new BigDecimal("100"), "USD")
        );

        acc.deposit(new Money(new BigDecimal("50"), "USD"));

        assertEquals(
                new BigDecimal("150"),
                acc.getBalance().getAmount()
        );
    }

    @Test
    void shouldNotAllowDifferentCurrency() {
        Account acc = new Account(
                UUID.randomUUID(),
                new Money(new BigDecimal("100"), "USD")
        );

        assertThrows(DomainError.class, () ->
                acc.deposit(new Money(new BigDecimal("50"), "EUR"))
        );
    }
}