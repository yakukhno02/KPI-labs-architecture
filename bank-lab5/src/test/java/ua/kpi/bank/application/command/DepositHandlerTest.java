package ua.kpi.bank.application.command;

import org.junit.jupiter.api.Test;
import ua.kpi.bank.application.FakeAccountRepository;
import ua.kpi.bank.core.application.command.CreateAccountCommand;
import ua.kpi.bank.core.application.command.CreateAccountHandler;
import ua.kpi.bank.core.application.command.DepositCommand;
import ua.kpi.bank.core.application.command.DepositHandler;
import ua.kpi.bank.core.api.MoneyDepositedEvent;
import ua.kpi.bank.core.application.service.AuditService;
import ua.kpi.bank.core.domain.factory.AccountFactory;
import ua.kpi.bank.core.domain.repository.AccountRepository;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

class DepositHandlerTest {

    @Test
    void shouldCallAuditService() {

        AccountRepository repo = new FakeAccountRepository();

        AuditService auditService = mock(AuditService.class);

        var publisher = mock(org.springframework.context.ApplicationEventPublisher.class);

        var factory = new AccountFactory();

        var create = new CreateAccountHandler(repo, factory);

        var id = create.handle(
                new CreateAccountCommand(new BigDecimal("100"), "USD")
        );

        var handler = new DepositHandler(
                repo,
                auditService,
                publisher
        );

        handler.handle(
                new DepositCommand(id, new BigDecimal("50"), "USD")
        );

        verify(auditService).log("Deposit: 50 USD");

        verify(publisher).publishEvent(any(MoneyDepositedEvent.class));
    }
}