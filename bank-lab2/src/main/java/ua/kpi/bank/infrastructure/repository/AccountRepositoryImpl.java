package ua.kpi.bank.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ua.kpi.bank.domain.model.Account;
import ua.kpi.bank.domain.repository.AccountRepository;
import ua.kpi.bank.infrastructure.mapper.AccountMapper;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {

    private final JpaAccountRepository jpaRepository;

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