package ua.kpi.bank.application.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.kpi.bank.domain.factory.AccountFactory;
import ua.kpi.bank.domain.repository.AccountRepository;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CreateAccountHandler {

    private final AccountRepository accountRepository;
    private final AccountFactory accountFactory;

    public UUID handle(CreateAccountCommand command) {
        var account = accountFactory.createAccount(
                command.amount(),
                command.currency()
        );

        accountRepository.save(account);

        return account.getId();
    }
}