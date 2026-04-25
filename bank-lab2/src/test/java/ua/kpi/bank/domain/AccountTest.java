package ua.kpi.bank.domain;

import org.junit.jupiter.api.Test;
import ua.kpi.bank.domain.exception.DomainError;
import ua.kpi.bank.domain.model.Account;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void shouldNotAllowNegativeBalance() {
        assertThrows(DomainError.class, () ->
                new Account(UUID.randomUUID(), new BigDecimal("-10"), "USD")
        );
    }

    @Test
    void shouldNotAllowWithdrawMoreThanBalance() {
        Account acc = new Account(UUID.randomUUID(),
                new BigDecimal("100"), "USD");

        assertThrows(DomainError.class, () ->
                acc.withdraw(new BigDecimal("200"))
        );
    }

    @Test
    void shouldDepositMoney() {
        Account acc = new Account(UUID.randomUUID(),
                new BigDecimal("100"), "USD");

        acc.deposit(new BigDecimal("50"));

        assertEquals(new BigDecimal("150"), acc.getBalance());
    }
}