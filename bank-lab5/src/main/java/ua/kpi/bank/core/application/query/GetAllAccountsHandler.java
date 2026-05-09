package ua.kpi.bank.core.application.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.kpi.bank.core.application.dto.AccountResponse;
import ua.kpi.bank.core.domain.repository.AccountRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllAccountsHandler {

    private final AccountRepository accountRepository;

    public List<AccountResponse> handle(GetAllAccountsQuery query) {
        return accountRepository.findAll().stream()
                .map(acc -> new AccountResponse(
                        acc.getId(),
                        acc.getBalance().getAmount(),
                        acc.getBalance().getCurrency()
                ))
                .toList();
    }
}