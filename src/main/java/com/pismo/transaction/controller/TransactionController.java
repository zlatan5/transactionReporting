package com.pismo.transaction.controller;

import com.pismo.transaction.domain.entity.Transaction;
import com.pismo.transaction.domain.service.TransactionService;
import com.pismo.transaction.dto.request.TransactionRequest;
import com.pismo.transaction.dto.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    
    private final TransactionService transactionService;
    
    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<String>> createTransaction(@Valid @RequestBody TransactionRequest request) {
        Transaction transaction = transactionService.createTransaction(
            request.getAccountId(),
            request.getOperationTypeId(),
            request.getAmount()
        );
        
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Transaction created successfully with ID: " + transaction.getTransactionId()));
    }
}
