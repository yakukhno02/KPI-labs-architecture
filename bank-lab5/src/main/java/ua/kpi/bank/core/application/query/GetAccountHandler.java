package ua.kpi.bank.core.application.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.kpi.bank.core.application.dto.AccountResponse;
import ua.kpi.bank.core.domain.repository.AccountRepository;

@Component
@RequiredArgsConstructor
public class GetAccountHandler {

    private final AccountRepository accountRepository;

    public AccountResponse handle(GetAccountQuery query) {
        var acc = accountRepository.findById(query.id())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        return new AccountResponse(
                acc.getId(),
                acc.getBalance().getAmount(),
                acc.getBalance().getCurrency()
        );
    }
}