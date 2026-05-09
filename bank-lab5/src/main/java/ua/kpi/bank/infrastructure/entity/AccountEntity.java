package ua.kpi.bank.infrastructure.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class AccountEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private String currency;

    protected AccountEntity() {}

    public AccountEntity(UUID id, BigDecimal balance, String currency) {
        this.id = id;
        this.balance = balance;
        this.currency = currency;
    }

    public UUID getId() { return id; }
    public BigDecimal getBalance() { return balance; }
    public String getCurrency() { return currency; }

    public void setBalance(BigDecimal balance) { this.balance = balance; }
}