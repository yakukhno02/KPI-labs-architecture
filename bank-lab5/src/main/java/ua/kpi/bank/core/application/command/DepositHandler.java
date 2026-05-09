package ua.kpi.bank.core.application.command;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import ua.kpi.bank.core.application.event.MoneyDepositedEvent;
import ua.kpi.bank.core.application.service.AuditService;
import ua.kpi.bank.core.domain.model.Money;
import ua.kpi.bank.core.domain.repository.AccountRepository;

@Component
@RequiredArgsConstructor
public class DepositHandler {

    private final AccountRepository accountRepository;
    private final AuditService auditService;
    private final ApplicationEventPublisher publisher;

    public void handle(DepositCommand command) {
        var account = accountRepository.findById(command.accountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.deposit(new Money(
                command.amount(),
                command.currency()
        ));

        accountRepository.save(account);
        auditService.log(
                "Deposit: " + command.amount() + " " + command.currency()
        );
        publisher.publishEvent(
                new MoneyDepositedEvent(
                        account.getId(),
                        command.amount(),
                        command.currency()
                )
        );
    }
}