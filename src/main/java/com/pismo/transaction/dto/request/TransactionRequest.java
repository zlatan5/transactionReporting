package com.pismo.transaction.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class TransactionRequest {
    
    @NotNull(message = "Account ID is required")
    @JsonProperty("account_id")
    private Long accountId;
    
    @NotNull(message = "Operation type ID is required")
    @JsonProperty("operation_type_id")
    private Long operationTypeId;
    
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
    
    // Constructors
    public TransactionRequest() {}
    
    public TransactionRequest(Long accountId, Long operationTypeId, BigDecimal amount) {
        this.accountId = accountId;
        this.operationTypeId = operationTypeId;
        this.amount = amount;
    }
    
    // Getters and Setters
    public Long getAccountId() { return accountId; }
    public void setAccountId(Long accountId) { this.accountId = accountId; }
    
    public Long getOperationTypeId() { return operationTypeId; }
    public void setOperationTypeId(Long operationTypeId) { this.operationTypeId = operationTypeId; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}
