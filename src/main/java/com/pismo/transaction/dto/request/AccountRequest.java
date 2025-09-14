package com.pismo.transaction.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountRequest {
    
    @NotBlank(message = "Document number is required")
    @Size(min = 11, max = 14, message = "Document number must be between 11 and 14 characters")
    @JsonProperty("document_number")
    private String documentNumber;
    
    // Constructors
    public AccountRequest() {}
    
    public AccountRequest(String documentNumber) {
        this.documentNumber = documentNumber;
    }
    
    // Getters and Setters
    public String getDocumentNumber() { return documentNumber; }
    public void setDocumentNumber(String documentNumber) { this.documentNumber = documentNumber; }
}
