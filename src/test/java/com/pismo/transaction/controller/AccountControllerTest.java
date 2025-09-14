package com.pismo.transaction.controller;

import com.pismo.transaction.domain.entity.Account;
import com.pismo.transaction.domain.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
class AccountControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private AccountService accountService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    void createAccount_Success() throws Exception {
        // Given
        Account account = new Account("12345678901");
        account.setAccountId(1L);
        when(accountService.createAccount(any(String.class))).thenReturn(account);
        
        String requestBody = """
            {
                "document_number": "12345678901"
            }
            """;
        
        // When & Then
        mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.account_id").value(1))
                .andExpect(jsonPath("$.data.document_number").value("12345678901"));
    }
    
    @Test
    void getAccount_Success() throws Exception {
        // Given
        Account account = new Account("12345678901");
        account.setAccountId(1L);
        when(accountService.getAccountById(1L)).thenReturn(account);
        
        // When & Then
        mockMvc.perform(get("/accounts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.account_id").value(1))
                .andExpect(jsonPath("$.document_number").value("12345678901"));
    }
}
