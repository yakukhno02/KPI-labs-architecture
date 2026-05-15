package ua.kpi.bank.application.command;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import ua.kpi.bank.application.event.MoneyDepositedEvent;
import ua.kpi.bank.application.service.AuditService;
import ua.kpi.bank.domain.model.Money;
import ua.kpi.bank.domain.repository.AccountRepository;

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

        try {
            auditService.log("Deposit: " + command.amount() + " " + command.currency());
        } catch (Exception e) {
            System.out.println("Sync audit failed");
        }

        publisher.publishEvent(
                new MoneyDepositedEvent(
                        account.getId(),
                        command.amount(),
                        command.currency()
                )
        );
    }
}