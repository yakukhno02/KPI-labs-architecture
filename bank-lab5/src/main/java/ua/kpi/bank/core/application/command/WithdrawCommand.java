package ua.kpi.bank.core.application.command;

import java.math.BigDecimal;
import java.util.UUID;

public record WithdrawCommand(
        UUID accountId,
        BigDecimal amount,
        String currency
) {}