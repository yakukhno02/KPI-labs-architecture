package ua.kpi.bank.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.kpi.bank.domain.factory.AccountFactory;
import ua.kpi.bank.domain.model.Account;
import ua.kpi.bank.domain.repository.AccountRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountFactory accountFactory;

    public Account createAccount(BigDecimal amount, String currency) {
        Account account = accountFactory.createAccount(amount, currency);
        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccount(UUID id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }
}