package ua.kpi.bank.core.api;

import java.math.BigDecimal;
import java.util.UUID;

public record MoneyDepositedEvent(
        UUID accountId,
        BigDecimal amount,
        String currency
) {
}