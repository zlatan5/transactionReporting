package com.pismo.transaction.service;

import com.pismo.transaction.domain.entity.Account;
import com.pismo.transaction.domain.entity.OperationType;
import com.pismo.transaction.domain.entity.Transaction;
import com.pismo.transaction.domain.repository.OperationTypeRepository;
import com.pismo.transaction.domain.repository.TransactionRepository;
import com.pismo.transaction.domain.service.AccountService;
import com.pismo.transaction.domain.service.TransactionService;
import com.pismo.transaction.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {
    
    @Mock
    private TransactionRepository transactionRepository;
    
    @Mock
    private OperationTypeRepository operationTypeRepository;
    
    @Mock
    private AccountService accountService;
    
    @InjectMocks
    private TransactionService transactionService;
    
    private Account testAccount;
    private OperationType cashPurchaseType;
    private OperationType paymentType;
    
    @BeforeEach
    void setUp() {
        testAccount = new Account("12345678901");
        testAccount.setAccountId(1L);
        
        cashPurchaseType = new OperationType(1L, "CASH PURCHASE");
        paymentType = new OperationType(4L, "PAYMENT");
    }
    
    @Test
    void createTransaction_CashPurchase_AmountShouldBeNegative() {
        // Given
        Long accountId = 1L;
        Long operationTypeId = 1L;
        BigDecimal amount = new BigDecimal("50.00");
        
        Transaction expectedTransaction = new Transaction(testAccount, cashPurchaseType, amount.negate());
        expectedTransaction.setTransactionId(1L);
        
        when(accountService.getAccountById(accountId)).thenReturn(testAccount);
        when(operationTypeRepository.findById(operationTypeId)).thenReturn(Optional.of(cashPurchaseType));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(expectedTransaction);
        
        // When
        Transaction result = transactionService.createTransaction(accountId, operationTypeId, amount);
        
        // Then
        assertNotNull(result);
        assertEquals(amount.negate(), result.getAmount());
        verify(accountService).getAccountById(accountId);
        verify(operationTypeRepository).findById(operationTypeId);
        verify(transactionRepository).save(any(Transaction.class));
    }
    
    @Test
    void createTransaction_Payment_AmountShouldBePositive() {
        // Given
        Long accountId = 1L;
        Long operationTypeId = 4L;
        BigDecimal amount = new BigDecimal("100.00");
        
        Transaction expectedTransaction = new Transaction(testAccount, paymentType, amount);
        expectedTransaction.setTransactionId(1L);
        
        when(accountService.getAccountById(accountId)).thenReturn(testAccount);
        when(operationTypeRepository.findById(operationTypeId)).thenReturn(Optional.of(paymentType));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(expectedTransaction);
        
        // When
        Transaction result = transactionService.createTransaction(accountId, operationTypeId, amount);
        
        // Then
        assertNotNull(result);
        assertEquals(amount, result.getAmount());
    }
    
    @Test
    void createTransaction_InvalidOperationType_ThrowsException() {
        // Given
        Long accountId = 1L;
        Long invalidOperationTypeId = 999L;
        BigDecimal amount = new BigDecimal("50.00");
        
        when(accountService.getAccountById(accountId)).thenReturn(testAccount);
        when(operationTypeRepository.findById(invalidOperationTypeId)).thenReturn(Optional.empty());
        
        // When & Then
        assertThrows(ResourceNotFoundException.class, 
            () -> transactionService.createTransaction(accountId, invalidOperationTypeId, amount));
    }
}
