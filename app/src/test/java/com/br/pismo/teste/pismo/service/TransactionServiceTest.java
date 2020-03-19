package com.br.pismo.teste.pismo.service;

import com.br.pismo.teste.pismo.dto.TransactionDTO;
import com.br.pismo.teste.pismo.entity.Account;
import com.br.pismo.teste.pismo.entity.OperationTypes;
import com.br.pismo.teste.pismo.repository.AccountRepository;
import com.br.pismo.teste.pismo.repository.OperationTypeRepository;
import com.br.pismo.teste.pismo.repository.TransactionRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	private AccountService accountService;
	
	@Mock
	private TransactionRepository transactionRepository;
	
	
	@Test
	public void testCreateTransaction() {
		Account account = new Account();
		account.setId(1L);
		account.setDocumentNumber("1");
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
		when(accountService.validityAccountDontHaveCreditToTransaction(anyLong(), any(), anyBoolean())).thenReturn(false);
		when(transactionRepository.save(any())).thenReturn(any());
		transactionService.create(transactionCreditDTO);
		transactionService.create(transactionDebitDTO);
		verify(accountRepository, times(2)).findById(anyLong());
		verify(operationTypeRepository, times(2)).findById(anyLong());
		verify(transactionRepository, times(2)).save(any());
		
	}
	
	@Test
	public void testCreateTransactionDontHaveCreditInAccount() {
		Account account = new Account();
		account.setId(1L);
		account.setDocumentNumber("1");
		account.setAvailableCreditLimit(new BigDecimal(20));
		OperationTypes operationTypesDebit = new OperationTypes();
		operationTypesDebit.setCreditType(false);
		operationTypesDebit.setDescription("description");
		operationTypesDebit.setId(2L);
		
		TransactionDTO transactionDebitDTO = createTransactionDebit();
		transactionDebitDTO.setAmount(new BigDecimal(30));
		
		when(accountRepository.findById(anyLong())).thenReturn(Optional.of(account));
		when(operationTypeRepository.findById(transactionDebitDTO.getOperationTypesId())).thenReturn(Optional.of(operationTypesDebit));
		when(accountService.validityAccountDontHaveCreditToTransaction(anyLong(), any(), anyBoolean())).thenReturn(true);
		when(transactionRepository.save(any())).thenReturn(any());
		
		ResponseEntity<?> responseEntity = transactionService.create(transactionDebitDTO);
		
		
		Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
		verify(accountRepository, times(1)).findById(anyLong());
		verify(operationTypeRepository, times(1)).findById(anyLong());
		verify(transactionRepository, times(0)).save(any());
		verify(accountService, times(1)).validityAccountDontHaveCreditToTransaction(anyLong(), any(), anyBoolean());
		
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
