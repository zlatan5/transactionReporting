package com.pismo.transaction.config;

import com.pismo.transaction.domain.entity.OperationType;
import com.pismo.transaction.domain.repository.OperationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private OperationTypeRepository operationTypeRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Initialize operation types if they don't exist
        if (operationTypeRepository.count() == 0) {
            operationTypeRepository.save(new OperationType(1L, "CASH PURCHASE"));
            operationTypeRepository.save(new OperationType(2L, "INSTALLMENT PURCHASE"));
            operationTypeRepository.save(new OperationType(3L, "WITHDRAWAL"));
            operationTypeRepository.save(new OperationType(4L, "PAYMENT"));
            System.out.println("Operation types initialized successfully");
        }
    }
}
