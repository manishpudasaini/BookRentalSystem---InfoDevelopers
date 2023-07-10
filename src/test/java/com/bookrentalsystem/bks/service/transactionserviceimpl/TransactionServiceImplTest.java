package com.bookrentalsystem.bks.service.transactionserviceimpl;

import com.bookrentalsystem.bks.model.Transaction;
import com.bookrentalsystem.bks.repo.TransactionRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {
    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private TransactionRepo transactionRepo;

    private Transaction transaction;

    @BeforeEach
    void setUp() {
       transaction = Transaction.builder()
                .id((short)9)
                .code("81231")
                .to(LocalDate.now())
                .from(LocalDate.now()).build();
    }

    @Test
    @DisplayName("should save the transaction in db")
    void shouldSaveTransactionInDb() {
        when(transactionRepo.save(transaction)).thenReturn(transaction);

       Transaction transaction1 =  transactionService.saveTransaction(transaction);


        assertThat(transaction1.getCode()).isNotNull();
    }
}