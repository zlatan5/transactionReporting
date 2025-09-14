package com.pismo.transaction.domain.service;

import com.pismo.transaction.domain.entity.Account;
import com.pismo.transaction.domain.entity.OperationType;
import com.pismo.transaction.domain.entity.Transaction;
import com.pismo.transaction.domain.repository.OperationTypeRepository;
import com.pismo.transaction.domain.repository.TransactionRepository;
import com.pismo.transaction.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
@Transactional
public class TransactionService {
    
    private final TransactionRepository transactionRepository;
    private final OperationTypeRepository operationTypeRepository;
    private final AccountService accountService;
    
    @Autowired
    public TransactionService(TransactionRepository transactionRepository, 
                            OperationTypeRepository operationTypeRepository,
                            AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.operationTypeRepository = operationTypeRepository;
        this.accountService = accountService;
    }
    
    /**
     * Creates a new transaction following business rules:
     * - Purchase and withdrawal transactions are recorded with negative values
     * - Payment transactions are recorded with positive values
     */
    public Transaction createTransaction(Long accountId, Long operationTypeId, BigDecimal amount) {
        // Validate account exists
        Account account = accountService.getAccountById(accountId);
        
        // Validate operation type exists
        OperationType operationType = operationTypeRepository.findById(operationTypeId)
            .orElseThrow(() -> new ResourceNotFoundException("Operation type not found with ID: " + operationTypeId));
        
        // Apply business rules for amount sign
        BigDecimal finalAmount = calculateFinalAmount(operationTypeId, amount);
        
        Transaction transaction = new Transaction(account, operationType, finalAmount);
        return transactionRepository.save(transaction);
    }
    
    /**
     * Applies business rules to determine the correct amount sign
     * Operations 1, 2, 3 (purchases and withdrawals) should be negative
     * Operation 4 (payment) should be positive
     */
    private BigDecimal calculateFinalAmount(Long operationTypeId, BigDecimal amount) {
        // Ensure we work with absolute value first
        BigDecimal absAmount = amount.abs();
        
        return switch (operationTypeId.intValue()) {
            case 1, 2, 3 -> absAmount.negate(); // CASH PURCHASE, INSTALLMENT PURCHASE, WITHDRAWAL
            case 4 -> absAmount; // PAYMENT
            default -> amount; // For any other operation types
        };
    }
}
