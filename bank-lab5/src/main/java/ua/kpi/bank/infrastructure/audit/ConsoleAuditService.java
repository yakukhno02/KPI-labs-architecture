package ua.kpi.bank.infrastructure.audit;

import org.springframework.stereotype.Service;
import ua.kpi.bank.application.service.AuditService;

@Service
public class ConsoleAuditService implements AuditService {

    @Override
    public void log(String message) {
        System.out.println("[AUDIT] " + message);
    }
}