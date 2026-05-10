package ua.kpi.bank.core.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class AccountResponse {
    public UUID id;
    public BigDecimal balance;
    public String currency;

    public AccountResponse(UUID id, BigDecimal balance, String currency) {
        this.id = id;
        this.balance = balance;
        this.currency = currency;
    }
}