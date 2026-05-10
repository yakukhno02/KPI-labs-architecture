package ua.kpi.bank.core.application.command;

import java.math.BigDecimal;

public record CreateAccountCommand(
        BigDecimal amount,
        String currency
) {
}