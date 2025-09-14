package com.pismo.transaction.controller;

import com.pismo.transaction.domain.entity.Account;
import com.pismo.transaction.domain.service.AccountService;
import com.pismo.transaction.dto.request.AccountRequest;
import com.pismo.transaction.dto.response.AccountResponse;
import com.pismo.transaction.dto.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    
    private final AccountService accountService;
    
    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<AccountResponse>> createAccount(@Valid @RequestBody AccountRequest request) {
        Account account = accountService.createAccount(request.getDocumentNumber());
        AccountResponse response = new AccountResponse(account.getAccountId(), account.getDocumentNumber());
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Account created successfully", response));
    }
    
    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable Long accountId) {
        Account account = accountService.getAccountById(accountId);
        AccountResponse response = new AccountResponse(account.getAccountId(), account.getDocumentNumber());
        return ResponseEntity.ok(response);
    }
}
