package ua.kpi.bank.application;

import org.junit.jupiter.api.Test;
import ua.kpi.bank.application.service.AccountService;
import ua.kpi.bank.domain.factory.AccountFactory;
import ua.kpi.bank.domain.model.Account;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    @Test
    void shouldCreateAccount() {
        var repo = new FakeAccountRepository();
        var factory = new AccountFactory();
        var service = new AccountService(repo, factory);

        Account acc = service.createAccount(new BigDecimal("100"), "USD");

        assertNotNull(acc.getId());
        assertEquals(new BigDecimal("100"), acc.getBalance().getAmount());
    }

    @Test
    void shouldGetAccount() {
        var repo = new FakeAccountRepository();
        var factory = new AccountFactory();
        var service = new AccountService(repo, factory);

        Account created = service.createAccount(new BigDecimal("100"), "USD");

        Account found = service.getAccount(created.getId());

        assertEquals(created.getId(), found.getId());
    }
}
