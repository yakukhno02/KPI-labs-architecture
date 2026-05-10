package ua.kpi.bank.core.infrastructure.audit;

import org.springframework.stereotype.Service;
import ua.kpi.bank.core.application.service.AuditService;

@Service
public class ConsoleAuditService implements AuditService {

    @Override
    public void log(String message) {
        System.out.println("[AUDIT] " + message);
    }
}