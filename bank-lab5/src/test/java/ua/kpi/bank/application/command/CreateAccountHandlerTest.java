package ua.kpi.bank.application.command;

import org.junit.jupiter.api.Test;
import ua.kpi.bank.application.FakeAccountRepository;
import ua.kpi.bank.core.application.command.CreateAccountCommand;
import ua.kpi.bank.core.application.command.CreateAccountHandler;
import ua.kpi.bank.core.domain.factory.AccountFactory;
import ua.kpi.bank.core.domain.repository.AccountRepository;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CreateAccountHandlerTest {

    @Test
    void shouldCreateAccount() {
        AccountRepository repo = new FakeAccountRepository();
        AccountFactory factory = new AccountFactory();

        CreateAccountHandler handler =
                new CreateAccountHandler(repo, factory);

        var id = handler.handle(
                new CreateAccountCommand(
                        new BigDecimal("100"),
                        "USD"
                )
        );

        var acc = repo.findById(id).orElseThrow();

        assertEquals(new BigDecimal("100"), acc.getBalance().getAmount());
        assertEquals("USD", acc.getBalance().getCurrency());
    }
}