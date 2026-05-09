package ua.kpi.bank.analytics.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ua.kpi.bank.analytics.acl.AnalyticsTranslator;
import ua.kpi.bank.core.application.event.MoneyDepositedEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class AnalyticsListener {

    private final AnalyticsTranslator translator;

    @Async
    @EventListener
    public void handle(MoneyDepositedEvent event) {

        var analytics = translator.fromEvent(event);

        log.info(
                "Analytics saved: {} {}",
                analytics.amount(),
                analytics.currency()
        );
    }
}