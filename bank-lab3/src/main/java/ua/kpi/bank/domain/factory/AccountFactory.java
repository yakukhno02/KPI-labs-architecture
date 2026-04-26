package ua.kpi.bank.domain.factory;

import org.springframework.stereotype.Component;
import ua.kpi.bank.domain.model.Account;
import ua.kpi.bank.domain.model.Money;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class AccountFactory {

    public Account createAccount(BigDecimal amount, String currency) {
        Money money = new Money(amount, currency);
        return new Account(UUID.randomUUID(), money);
    }
}