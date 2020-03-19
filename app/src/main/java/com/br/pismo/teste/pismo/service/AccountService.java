package com.br.pismo.teste.pismo.service;


import com.br.pismo.teste.pismo.dto.AccountDTO;
import com.br.pismo.teste.pismo.entity.Account;
import com.br.pismo.teste.pismo.repository.AccountRepository;
import com.br.pismo.teste.pismo.response.AccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	
	public ResponseEntity<?> get(Long id) {
		
		Optional<Account> optionalAccount = accountRepository.findById(id);
		
		
		return optionalAccount.isPresent() ? ResponseEntity.ok(new AccountResponse(optionalAccount.get())) : ResponseEntity.notFound().build();
	}
	
	public ResponseEntity<?> create(AccountDTO accountDTO) {
		Optional<Account> optionalAccount = accountRepository.findByDocumentNumber(accountDTO.getDocumentNumber());
		
		if (optionalAccount.isPresent()) return new ResponseEntity<>("Document exist", HttpStatus.BAD_REQUEST);
		Account account = new Account();
		account.setDocumentNumber(accountDTO.getDocumentNumber());
		account.setAvailableCreditLimit(accountDTO.getAvailableCreditLimit());
		Account accountSave = accountRepository.save(account);
		return ResponseEntity.ok(new AccountResponse(accountSave));
	}
	
	public boolean validityAccountDontHaveCreditToTransaction(Long idAccount, BigDecimal value, Boolean isCredit) {
		updateAmount(isCredit, idAccount, value);
		if (!isCredit) {
			Optional<Account> optionalAccount = accountRepository.findById(idAccount);
			return optionalAccount.map(account -> account.getAvailableCreditLimit().subtract(value).signum() == -1).orElse(false);
		}
		
		return false;
		
	}
	
	public Optional<Account> updateAmount(Boolean isCredit, Long idAccount, BigDecimal value) {
		
		Optional<Account> optionalAccount = accountRepository.findById(idAccount);
		if (optionalAccount.isPresent()) {
			Account account = optionalAccount.get();
			account.setAvailableCreditLimit(isCredit ? account.getAvailableCreditLimit().add(value) : account.getAvailableCreditLimit().subtract(value));
			return Optional.of(accountRepository.save(account));
		}
		return Optional.empty();
	}
	
}
