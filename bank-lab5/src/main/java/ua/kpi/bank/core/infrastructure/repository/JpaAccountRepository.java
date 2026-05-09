package ua.kpi.bank.core.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kpi.bank.core.infrastructure.entity.AccountEntity;

import java.util.UUID;

public interface JpaAccountRepository extends JpaRepository<AccountEntity, UUID> {
}