package ua.kpi.bank.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kpi.bank.infrastructure.entity.AccountEntity;

import java.util.UUID;

public interface JpaAccountRepository extends JpaRepository<AccountEntity, UUID> {
}