package com.br.pismo.teste.pismo.service;

import com.br.pismo.teste.pismo.dto.TransactionDTO;
import com.br.pismo.teste.pismo.entity.Account;
import com.br.pismo.teste.pismo.entity.OperationTypes;
import com.br.pismo.teste.pismo.repository.AccountRepository;
import com.br.pismo.teste.pismo.repository.OperationTypeRepository;
import com.br.pismo.teste.pismo.repository.TransactionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class TransactionServiceTest {
	
	
	@InjectMocks
	private TransactionService transactionService;
	
	@Mock
	private AccountRepository accountRepository;
	
	@Mock
	private OperationTypeRepository operationTypeRepository;
	
	@Mock
	private TransactionRepository transactionRepository;
	
	
	@Test
	public void testCreateAccount() {
		Account account = new Account();
		account.setId(1L);
		account.setDocumentNumber(1L);
		OperationTypes operationTypesCredit = new OperationTypes();
		operationTypesCredit.setCreditType(true);
		operationTypesCredit.setDescription("description");
		operationTypesCredit.setId(1L);
		OperationTypes operationTypesDebit = new OperationTypes();
		operationTypesDebit.setCreditType(false);
		operationTypesDebit.setDescription("description");
		operationTypesDebit.setId(2L);
		
		TransactionDTO transactionCreditDTO = createTransactionCredit();
		
		TransactionDTO transactionDebitDTO = createTransactionDebit();
		
		when(accountRepository.findById(anyLong())).thenReturn(Optional.of(account));
		when(operationTypeRepository.findById(transactionCreditDTO.getOperationTypesId())).thenReturn(Optional.of(operationTypesCredit));
		when(operationTypeRepository.findById(transactionDebitDTO.getOperationTypesId())).thenReturn(Optional.of(operationTypesDebit));
		
		when(transactionRepository.save(any())).thenReturn(any());
		transactionService.create(transactionCreditDTO);
		transactionService.create(transactionDebitDTO);
		verify(accountRepository, times(2)).findById(anyLong());
		verify(operationTypeRepository, times(2)).findById(anyLong());
		verify(transactionRepository, times(2)).save(any());
		
	}
	
	private TransactionDTO createTransactionCredit() {
		TransactionDTO transactionCreditDTO = new TransactionDTO();
		transactionCreditDTO.setAccountId(1L);
		transactionCreditDTO.setAmount(BigDecimal.TEN);
		transactionCreditDTO.setOperationTypesId(1L);
		return transactionCreditDTO;
	}
	
	private TransactionDTO createTransactionDebit() {
		TransactionDTO transactionDebitDTO = new TransactionDTO();
		transactionDebitDTO.setAccountId(1L);
		transactionDebitDTO.setAmount(BigDecimal.TEN);
		transactionDebitDTO.setOperationTypesId(2L);
		return transactionDebitDTO;
	}
	
	
}
