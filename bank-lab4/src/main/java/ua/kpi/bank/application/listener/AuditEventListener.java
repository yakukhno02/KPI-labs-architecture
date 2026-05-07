package ua.kpi.bank.application.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ua.kpi.bank.application.event.MoneyDepositedEvent;

@Component
public class AuditEventListener {

    @EventListener
    public void handle(MoneyDepositedEvent event) {

        System.out.println(
                "[ASYNC AUDIT] Deposit: "
                        + event.amount()
                        + " "
                        + event.currency()
        );
    }
}