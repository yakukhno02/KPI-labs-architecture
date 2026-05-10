package ua.kpi.bank.presentation.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.kpi.bank.core.application.command.*;
import ua.kpi.bank.core.application.dto.AccountResponse;
import ua.kpi.bank.core.application.dto.CreateAccountResponse;
import ua.kpi.bank.core.application.query.GetAccountHandler;
import ua.kpi.bank.core.application.query.GetAccountQuery;
import ua.kpi.bank.core.application.query.GetAllAccountsHandler;
import ua.kpi.bank.core.application.query.GetAllAccountsQuery;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final CreateAccountHandler createHandler;
    private final DepositHandler depositHandler;
    private final WithdrawHandler withdrawHandler;
    private final GetAllAccountsHandler getAllHandler;
    private final GetAccountHandler getAccountHandler;

    @PostMapping
    public CreateAccountResponse create(@RequestBody CreateAccountCommand command) {
        UUID id = createHandler.handle(command);
        return new CreateAccountResponse(id);
    }

    @GetMapping
    public List<AccountResponse> getAll() {
        return getAllHandler.handle(new GetAllAccountsQuery());
    }

    @GetMapping("/{id}")
    public AccountResponse get(@PathVariable UUID id) {
        return getAccountHandler.handle(new GetAccountQuery(id));
    }

    @PostMapping("/{id}/deposit")
    public void deposit(@PathVariable UUID id,
                        @RequestBody DepositCommand body) {

        depositHandler.handle(
                new DepositCommand(
                        id,
                        body.amount(),
                        body.currency()
                )
        );
    }

    @PostMapping("/{id}/withdraw")
    public void withdraw(@PathVariable UUID id,
                         @RequestBody DepositCommand body) {

        withdrawHandler.handle(
                new WithdrawCommand(
                        id,
                        body.amount(),
                        body.currency()
                )
        );
    }
}