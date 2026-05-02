package ua.kpi.bank.application.command;

import org.junit.jupiter.api.Test;
import ua.kpi.bank.application.FakeAccountRepository;
import ua.kpi.bank.domain.factory.AccountFactory;
import ua.kpi.bank.domain.repository.AccountRepository;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class DepositHandlerTest {

    @Test
    void shouldDepositMoney() {
        AccountRepository repo = new FakeAccountRepository();
        AccountFactory factory = new AccountFactory();

        CreateAccountHandler create =
                new CreateAccountHandler(repo, factory);

        DepositHandler deposit =
                new DepositHandler(repo);

        var id = create.handle(
                new CreateAccountCommand(
                        new BigDecimal("100"),
                        "USD"
                )
        );

        deposit.handle(
                new DepositCommand(
                        id,
                        new BigDecimal("50"),
                        "USD"
                )
        );

        var acc = repo.findById(id).orElseThrow();

        assertEquals(new BigDecimal("150"), acc.getBalance().getAmount());
    }
}