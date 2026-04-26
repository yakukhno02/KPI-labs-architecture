package ua.kpi.bank.domain.repository;

import ua.kpi.bank.domain.model.Account;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {

    List<Account> findAll();

    Optional<Account> findById(UUID id);

    Account save(Account account);
}