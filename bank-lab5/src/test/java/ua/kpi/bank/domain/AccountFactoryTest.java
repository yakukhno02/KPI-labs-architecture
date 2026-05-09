package ua.kpi.bank.domain;

import org.junit.jupiter.api.Test;
import ua.kpi.bank.core.domain.factory.AccountFactory;
import ua.kpi.bank.core.domain.model.Account;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountFactoryTest {

    @Test
    void shouldCreateValidAccount() {
        AccountFactory factory = new AccountFactory();

        Account acc = factory.createAccount(new BigDecimal("100"), "USD");

        assertEquals(new BigDecimal("100"), acc.getBalance().getAmount());
    }
}