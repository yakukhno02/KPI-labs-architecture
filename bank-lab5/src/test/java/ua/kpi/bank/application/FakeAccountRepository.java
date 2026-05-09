package ua.kpi.bank.application;

import ua.kpi.bank.core.domain.model.Account;
import ua.kpi.bank.core.domain.repository.AccountRepository;

import java.util.*;

public class FakeAccountRepository implements AccountRepository {

    private final Map<UUID, Account> storage = new HashMap<>();

    @Override
    public List<Account> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<Account> findById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Account save(Account account) {
        storage.put(account.getId(), account);
        return account;
    }
}