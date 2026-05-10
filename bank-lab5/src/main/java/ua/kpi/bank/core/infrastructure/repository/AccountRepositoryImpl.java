package ua.kpi.bank.core.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ua.kpi.bank.core.domain.model.Account;
import ua.kpi.bank.core.domain.repository.AccountRepository;
import ua.kpi.bank.core.infrastructure.mapper.AccountMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {

    private final JpaAccountRepository jpaRepository;

    @Override
    public List<Account> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(AccountMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Account> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(AccountMapper::toDomain);
    }

    @Override
    public Account save(Account account) {
        var entity = AccountMapper.toEntity(account);
        var saved = jpaRepository.save(entity);
        return AccountMapper.toDomain(saved);
    }
}