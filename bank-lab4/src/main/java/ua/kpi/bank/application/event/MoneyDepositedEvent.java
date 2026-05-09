package ua.kpi.bank.application.event;

import java.math.BigDecimal;
import java.util.UUID;

public record MoneyDepositedEvent(
        UUID accountId,
        BigDecimal amount,
        String currency
) {
}