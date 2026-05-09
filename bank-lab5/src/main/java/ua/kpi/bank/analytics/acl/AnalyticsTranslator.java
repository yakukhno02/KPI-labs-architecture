package ua.kpi.bank.analytics.acl;

import org.springframework.stereotype.Component;
import ua.kpi.bank.analytics.domain.TransactionAnalytics;
import ua.kpi.bank.core.api.MoneyDepositedEvent;

import java.time.LocalDateTime;

@Component
public class AnalyticsTranslator {

    public TransactionAnalytics fromEvent(
            MoneyDepositedEvent event
    ) {
        return new TransactionAnalytics(
                event.accountId(),
                event.amount(),
                event.currency(),
                LocalDateTime.now()
        );
    }
}