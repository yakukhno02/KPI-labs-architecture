package ua.kpi.bank.domain.repository;

import ua.kpi.bank.domain.model.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {

    Optional<Account> findById(UUID id);

    Account save(Account account);
}