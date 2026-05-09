package ua.kpi.bank.core.domain.exception;

public class DomainError extends RuntimeException {
    public DomainError(String message) {
        super(message);
    }
}
