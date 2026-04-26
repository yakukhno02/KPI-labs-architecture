package ua.kpi.bank.application.command;

import org.junit.jupiter.api.Test;
import ua.kpi.bank.application.FakeAccountRepository;
import ua.kpi.bank.domain.factory.AccountFactory;
import ua.kpi.bank.domain.repository.AccountRepository;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class WithdrawHandlerTest {

    @Test
    void shouldWithdrawMoney() {
        AccountRepository repo = new FakeAccountRepository();
        AccountFactory factory = new AccountFactory();

        CreateAccountHandler create =
                new CreateAccountHandler(repo, factory);

        WithdrawHandler withdraw =
                new WithdrawHandler(repo);

        var id = create.handle(
                new CreateAccountCommand(
                        new BigDecimal("100"),
                        "USD"
                )
        );

        withdraw.handle(
                new WithdrawCommand(
                        id,
                        new BigDecimal("40"),
                        "USD"
                )
        );

        var acc = repo.findById(id).orElseThrow();

        assertEquals(new BigDecimal("60"), acc.getBalance().getAmount());
    }

    @Test
    void shouldFailWhenNotEnoughMoney() {
        AccountRepository repo = new FakeAccountRepository();
        AccountFactory factory = new AccountFactory();

        CreateAccountHandler create =
                new CreateAccountHandler(repo, factory);

        WithdrawHandler withdraw =
                new WithdrawHandler(repo);

        var id = create.handle(
                new CreateAccountCommand(
                        new BigDecimal("100"),
                        "USD"
                )
        );

        assertThrows(RuntimeException.class, () ->
                withdraw.handle(
                        new WithdrawCommand(
                                id,
                                new BigDecimal("200"),
                                "USD"
                        )
                )
        );
    }
}