package ua.kpi.bank.presentation.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.kpi.bank.application.dto.AccountResponse;
import ua.kpi.bank.application.dto.CreateAccountRequest;
import ua.kpi.bank.application.service.AccountService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public AccountResponse create(@RequestBody CreateAccountRequest request) {
        var acc = accountService.createAccount(
                request.amount,
                request.currency
        );

        return new AccountResponse(
                acc.getId(),
                acc.getBalance().getAmount(),
                acc.getBalance().getCurrency()
        );
    }

    @GetMapping
    public List<AccountResponse> getAll() {
        return accountService.getAllAccounts()
                .stream()
                .map(acc -> new AccountResponse(
                        acc.getId(),
                        acc.getBalance().getAmount(),
                        acc.getBalance().getCurrency()
                ))
                .toList();
    }

    @GetMapping("/{id}")
    public AccountResponse get(@PathVariable UUID id) {
        var acc = accountService.getAccount(id);

        return new AccountResponse(
                acc.getId(),
                acc.getBalance().getAmount(),
                acc.getBalance().getCurrency()
        );
    }

    @PostMapping("/{id}/deposit")
    public AccountResponse deposit(
            @PathVariable UUID id,
            @RequestBody CreateAccountRequest request
    ) {
        var acc = accountService.deposit(
                id,
                request.amount,
                request.currency
        );

        return new AccountResponse(
                acc.getId(),
                acc.getBalance().getAmount(),
                acc.getBalance().getCurrency()
        );
    }

    @PostMapping("/{id}/withdraw")
    public AccountResponse withdraw(
            @PathVariable UUID id,
            @RequestBody CreateAccountRequest request
    ) {
        var acc = accountService.withdraw(
                id,
                request.amount,
                request.currency
        );

        return new AccountResponse(
                acc.getId(),
                acc.getBalance().getAmount(),
                acc.getBalance().getCurrency()
        );
    }
}