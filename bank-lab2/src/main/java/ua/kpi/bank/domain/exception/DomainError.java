package ua.kpi.bank.domain.exception;

public class DomainError extends RuntimeException {
    public DomainError(String message) {
        super(message);
    }
}
