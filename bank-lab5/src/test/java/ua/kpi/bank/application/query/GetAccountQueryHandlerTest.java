package ua.kpi.bank.application.query;

import org.junit.jupiter.api.Test;
import ua.kpi.bank.application.FakeAccountRepository;
import ua.kpi.bank.application.command.CreateAccountCommand;
import ua.kpi.bank.application.command.CreateAccountHandler;
import ua.kpi.bank.domain.factory.AccountFactory;
import ua.kpi.bank.domain.repository.AccountRepository;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class GetAccountQueryHandlerTest {

    @Test
    void shouldReturnAccount() {
        AccountRepository repo = new FakeAccountRepository();
        AccountFactory factory = new AccountFactory();

        CreateAccountHandler create =
                new CreateAccountHandler(repo, factory);

        GetAccountHandler query =
                new GetAccountHandler(repo);

        var id = create.handle(
                new CreateAccountCommand(
                        new BigDecimal("100"),
                        "USD"
                )
        );

        var result = query.handle(new GetAccountQuery(id));

        assertEquals(new BigDecimal("100"), result.balance);
        assertEquals("USD", result.currency);
    }
}