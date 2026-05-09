package ua.kpi.bank.core.application.listener;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ua.kpi.bank.core.application.event.MoneyDepositedEvent;

@Component
public class AuditEventListener {

    @Async
    @EventListener
    public void handle(MoneyDepositedEvent event) throws InterruptedException {

        //Thread.sleep(5000);

        System.out.println(
                "[ASYNC AUDIT] Deposit: "
                        + event.amount()
                        + " "
                        + event.currency()
        );
    }
}