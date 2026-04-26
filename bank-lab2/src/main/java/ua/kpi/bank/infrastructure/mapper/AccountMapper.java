package ua.kpi.bank.infrastructure.mapper;

import ua.kpi.bank.domain.model.Account;
import ua.kpi.bank.domain.model.Money;
import ua.kpi.bank.infrastructure.entity.AccountEntity;

public class AccountMapper {

    public static Account toDomain(AccountEntity entity) {
        return new Account(
                entity.getId(),
                new Money(entity.getBalance(), entity.getCurrency())
        );
    }

    public static AccountEntity toEntity(Account account) {
        return new AccountEntity(
                account.getId(),
                account.getBalance().getAmount(),
                account.getBalance().getCurrency()
        );
    }
}