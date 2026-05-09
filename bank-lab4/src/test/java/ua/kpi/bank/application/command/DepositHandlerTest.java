package ua.kpi.bank.application.command;

import org.junit.jupiter.api.Test;
import ua.kpi.bank.application.FakeAccountRepository;
import ua.kpi.bank.application.service.AuditService;
import ua.kpi.bank.domain.factory.AccountFactory;
import ua.kpi.bank.domain.repository.AccountRepository;

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

        verify(publisher).publishEvent(any(ua.kpi.bank.application.event.MoneyDepositedEvent.class));
    }
}