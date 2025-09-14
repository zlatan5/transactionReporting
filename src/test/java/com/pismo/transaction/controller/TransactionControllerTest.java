package com.pismo.transaction.controller;

import com.pismo.transaction.domain.entity.Account;
import com.pismo.transaction.domain.entity.OperationType;
import com.pismo.transaction.domain.entity.Transaction;
import com.pismo.transaction.domain.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private TransactionService transactionService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    void createTransaction_Success() throws Exception {
        // Given
        Account account = new Account("12345678901");
        account.setAccountId(1L);
        
        OperationType operationType = new OperationType(1L, "CASH PURCHASE");
        
        Transaction transaction = new Transaction(account, operationType, new BigDecimal("-50.00"));
        transaction.setTransactionId(1L);
        
        when(transactionService.createTransaction(any(Long.class), any(Long.class), any(BigDecimal.class)))
            .thenReturn(transaction);
        
        String requestBody = """
            {
                "account_id": 1,
                "operation_type_id": 1,
                "amount": 50.00
            }
            """;
        
        // When & Then
        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Transaction created successfully with ID: 1"));
    }
}
