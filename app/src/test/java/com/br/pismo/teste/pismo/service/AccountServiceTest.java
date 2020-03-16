package com.br.pismo.teste.pismo.service;

import com.br.pismo.teste.pismo.dto.AccountDTO;
import com.br.pismo.teste.pismo.entity.Account;
import com.br.pismo.teste.pismo.repository.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

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
		when(accountRepository.findByDocumentNumber(accountDTO.getDocumentNumber())).thenReturn(Optional.empty());
		when(accountRepository.save(any())).thenReturn(any());
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
		Account account = new Account();
		account.setDocumentNumber(1L);
		account.setId(1L);
		when(accountRepository.findById(anyLong())).thenReturn(Optional.of(account));
		accountService.get(1L);
		verify(accountRepository, times(1)).findById(anyLong());
		
		
	}
	
	
	private AccountDTO createAccountDTO() {
		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setDocumentNumber(123L);
		accountDTO.setId(1L);
		return accountDTO;
	}
}
