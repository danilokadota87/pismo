package com.br.pismo.teste.pismo.service;

import com.br.pismo.teste.pismo.dto.AccountDTO;
import com.br.pismo.teste.pismo.entity.Account;
import com.br.pismo.teste.pismo.repository.AccountRepository;
import org.junit.Assert;
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
public class AccountServiceTest {
	
	
	@InjectMocks
	private AccountService accountService;
	
	@Mock
	private AccountRepository accountRepository;
	
	
	@Test
	public void testCreateAccount() {
		AccountDTO accountDTO = createAccountDTO();
		Account account = createAccount();
		when(accountRepository.findByDocumentNumber(accountDTO.getDocumentNumber())).thenReturn(Optional.empty());
		when(accountRepository.save(any())).thenReturn(account);
		accountService.create(accountDTO);
		verify(accountRepository, times(1)).findByDocumentNumber(accountDTO.getDocumentNumber());
		verify(accountRepository, times(1)).save(any());
		
		
	}
	
	@Test
	public void testCreateAccountToDocumentsExist() {
		AccountDTO accountDTO = createAccountDTO();
		when(accountRepository.findByDocumentNumber(accountDTO.getDocumentNumber())).thenReturn(Optional.of(new Account()));
		when(accountRepository.save(any())).thenReturn(any());
		accountService.create(accountDTO);
		verify(accountRepository, times(1)).findByDocumentNumber(accountDTO.getDocumentNumber());
		verify(accountRepository, times(0)).save(any());
		
		
	}
	
	@Test
	public void get() {
		Account account = createAccount();
		when(accountRepository.findById(anyLong())).thenReturn(Optional.of(account));
		accountService.get(1L);
		verify(accountRepository, times(1)).findById(anyLong());
		
	}
	
	@Test
	public void testUpdateValueAmountToCredit() {
		Account account = createAccount();
		when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));
		when(accountRepository.save(account)).thenReturn(account);
		Optional<Account> optionalAccount = accountService.updateAmount(true, account.getId(), new BigDecimal(10));
		Assert.assertEquals(optionalAccount.get().getAvailableCreditLimit(), new BigDecimal(20));
		verify(accountRepository, times(1)).save(any());
	}
	
	@Test
	public void testUpdateValueAmountToDebit() {
		Account account = createAccount();
		when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));
		when(accountRepository.save(account)).thenReturn(account);
		Optional<Account> optionalAccount = accountService.updateAmount(false, account.getId(), new BigDecimal(10));
		Assert.assertEquals(optionalAccount.get().getAvailableCreditLimit(), BigDecimal.ZERO);
		verify(accountRepository, times(1)).save(any());
	}
	
	private Account createAccount() {
		Account account = new Account();
		account.setDocumentNumber("1");
		account.setId(1L);
		account.setAvailableCreditLimit(new BigDecimal(10));
		return account;
	}
	
	@Test
	public void testValidationAccountDontHaveCredit() {
		Account account = createAccount();
		account.setAvailableCreditLimit(new BigDecimal(20));
		
		when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));
		when(accountRepository.save(account)).thenReturn(account);
		
		accountService.validityAccountDontHaveCreditToTransaction(account.getId(), new BigDecimal(10), false);
		
		verify(accountRepository, times(2)).findById(account.getId());
		verify(accountRepository, times(1)).save(account);
	}
	
	
	private AccountDTO createAccountDTO() {
		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setDocumentNumber("123");
		return accountDTO;
	}
}
