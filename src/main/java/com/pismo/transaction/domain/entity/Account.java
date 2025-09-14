package com.pismo.transaction.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;
    
    @NotBlank(message = "Document number is required")
    @Size(min = 11, max = 14, message = "Document number must be between 11 and 14 characters")
    @Column(name = "document_number", unique = true, nullable = false)
    private String documentNumber;
    
    // Constructors
    public Account() {}
    
    public Account(String documentNumber) {
        this.documentNumber = documentNumber;
    }
    
    // Getters and Setters
    public Long getAccountId() { return accountId; }
    public void setAccountId(Long accountId) { this.accountId = accountId; }
    
    public String getDocumentNumber() { return documentNumber; }
    public void setDocumentNumber(String documentNumber) { this.documentNumber = documentNumber; }
}
