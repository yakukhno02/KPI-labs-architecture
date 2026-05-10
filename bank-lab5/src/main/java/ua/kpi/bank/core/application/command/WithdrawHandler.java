package ua.kpi.bank.core.application.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.kpi.bank.core.domain.model.Money;
import ua.kpi.bank.core.domain.repository.AccountRepository;

@Component
@RequiredArgsConstructor
public class WithdrawHandler {

    private final AccountRepository accountRepository;

    public void handle(WithdrawCommand command) {
        var account = accountRepository.findById(command.accountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.withdraw(new Money(
                command.amount(),
                command.currency()
        ));

        accountRepository.save(account);
    }
}