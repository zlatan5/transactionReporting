package com.pismo.transaction.service;

import com.pismo.transaction.domain.entity.Account;
import com.pismo.transaction.domain.repository.AccountRepository;
import com.pismo.transaction.domain.service.AccountService;
import com.pismo.transaction.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    
    @Mock
    private AccountRepository accountRepository;
    
    @InjectMocks
    private AccountService accountService;
    
    private Account testAccount;
    
    @BeforeEach
    void setUp() {
        testAccount = new Account("12345678901");
        testAccount.setAccountId(1L);
    }
    
    @Test
    void createAccount_Success() {
        // Given
        String documentNumber = "12345678901";
        when(accountRepository.existsByDocumentNumber(documentNumber)).thenReturn(false);
        when(accountRepository.save(any(Account.class))).thenReturn(testAccount);
        
        // When
        Account result = accountService.createAccount(documentNumber);
        
        // Then
        assertNotNull(result);
        assertEquals(documentNumber, result.getDocumentNumber());
        verify(accountRepository).existsByDocumentNumber(documentNumber);
        verify(accountRepository).save(any(Account.class));
    }
    
    @Test
    void createAccount_DocumentNumberExists_ThrowsException() {
        // Given
        String documentNumber = "12345678901";
        when(accountRepository.existsByDocumentNumber(documentNumber)).thenReturn(true);
        
        // When & Then
        assertThrows(DataIntegrityViolationException.class, 
            () -> accountService.createAccount(documentNumber));
    }
    
    @Test
    void getAccountById_Success() {
        // Given
        Long accountId = 1L;
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(testAccount));
        
        // When
        Account result = accountService.getAccountById(accountId);
        
        // Then
        assertNotNull(result);
        assertEquals(accountId, result.getAccountId());
    }
    
    @Test
    void getAccountById_NotFound_ThrowsException() {
        // Given
        Long accountId = 999L;
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());
        
        // When & Then
        assertThrows(ResourceNotFoundException.class, 
            () -> accountService.getAccountById(accountId));
    }
}
