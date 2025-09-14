package com.pismo.transaction.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "operation_types")
public class OperationType {
    @Id
    @Column(name = "operation_type_id")
    private Long operationTypeId;
    
    @Column(name = "description", nullable = false)
    private String description;
    
    // Constructors
    public OperationType() {}
    
    public OperationType(Long operationTypeId, String description) {
        this.operationTypeId = operationTypeId;
        this.description = description;
    }
    
    // Getters and Setters
    public Long getOperationTypeId() { return operationTypeId; }
    public void setOperationTypeId(Long operationTypeId) { this.operationTypeId = operationTypeId; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
