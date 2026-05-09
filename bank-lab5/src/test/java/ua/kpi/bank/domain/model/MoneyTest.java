package ua.kpi.bank.domain.model;

import org.junit.jupiter.api.Test;
import ua.kpi.bank.core.domain.exception.DomainError;
import ua.kpi.bank.core.domain.model.Money;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void shouldNotAllowNegativeAmount() {
        assertThrows(DomainError.class, () ->
                new Money(new BigDecimal("-10"), "USD")
        );
    }

    @Test
    void shouldNotAllowEmptyCurrency() {
        assertThrows(DomainError.class, () ->
                new Money(new BigDecimal("10"), "")
        );
    }

    @Test
    void shouldAddMoney() {
        Money m1 = new Money(new BigDecimal("100"), "USD");
        Money m2 = new Money(new BigDecimal("50"), "USD");

        Money result = m1.add(m2);

        assertEquals(new BigDecimal("150"), result.getAmount());
    }

    @Test
    void shouldNotAddDifferentCurrency() {
        Money m1 = new Money(new BigDecimal("100"), "USD");
        Money m2 = new Money(new BigDecimal("50"), "EUR");

        assertThrows(DomainError.class, () ->
                m1.add(m2)
        );
    }

    @Test
    void shouldSubtractMoney() {
        Money m1 = new Money(new BigDecimal("100"), "USD");
        Money m2 = new Money(new BigDecimal("50"), "USD");

        Money result = m1.subtract(m2);

        assertEquals(new BigDecimal("50"), result.getAmount());
    }

    @Test
    void shouldNotAllowNegativeResult() {
        Money m1 = new Money(new BigDecimal("100"), "USD");
        Money m2 = new Money(new BigDecimal("200"), "USD");

        assertThrows(DomainError.class, () ->
                m1.subtract(m2)
        );
    }
}