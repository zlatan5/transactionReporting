package com.pismo.transaction.domain.service;

import com.pismo.transaction.domain.entity.Account;
import com.pismo.transaction.domain.repository.AccountRepository;
import com.pismo.transaction.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountService {
    
    private final AccountRepository accountRepository;
    
    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    
    /**
     * Creates a new account with the provided document number
     * @param documentNumber The document number for the account
     * @return The created account
     * @throws DataIntegrityViolationException if document number already exists
     */
    public Account createAccount(String documentNumber) {
        if (accountRepository.existsByDocumentNumber(documentNumber)) {
            throw new DataIntegrityViolationException("Account with document number " + documentNumber + " already exists");
        }
        
        Account account = new Account(documentNumber);
        return accountRepository.save(account);
    }
    
    /**
     * Retrieves an account by its ID
     * @param accountId The account ID
     * @return The account
     * @throws ResourceNotFoundException if account not found
     */
    @Transactional(readOnly = true)
    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId)
            .orElseThrow(() -> new ResourceNotFoundException("Account not found with ID: " + accountId));
    }
}
