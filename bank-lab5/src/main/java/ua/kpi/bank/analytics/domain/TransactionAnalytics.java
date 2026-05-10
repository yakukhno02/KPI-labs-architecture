package ua.kpi.bank.analytics.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionAnalytics(
        UUID accountId,
        BigDecimal amount,
        String currency,
        LocalDateTime createdAt
) {
}