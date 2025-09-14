package com.pismo.transaction.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountResponse {
    
    @JsonProperty("account_id")
    private Long accountId;
    
    @JsonProperty("document_number")
    private String documentNumber;
    
    // Constructors
    public AccountResponse() {}
    
    public AccountResponse(Long accountId, String documentNumber) {
        this.accountId = accountId;
        this.documentNumber = documentNumber;
    }
    
    // Getters and Setters
    public Long getAccountId() { return accountId; }
    public void setAccountId(Long accountId) { this.accountId = accountId; }
    
    public String getDocumentNumber() { return documentNumber; }
    public void setDocumentNumber(String documentNumber) { this.documentNumber = documentNumber; }
}
