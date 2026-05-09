package ua.kpi.bank.application.command;

import java.math.BigDecimal;

public record CreateAccountCommand(
        BigDecimal amount,
        String currency
) {
}