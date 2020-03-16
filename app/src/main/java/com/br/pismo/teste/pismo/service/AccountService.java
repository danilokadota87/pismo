package com.br.pismo.teste.pismo.service;


import com.br.pismo.teste.pismo.dto.AccountDTO;
import com.br.pismo.teste.pismo.entity.Account;
import com.br.pismo.teste.pismo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	
	public ResponseEntity<?> get(Long id) {
		
		Optional<Account> optionalAccount = accountRepository.findById(id);
		
		
		return optionalAccount.isPresent() ? ResponseEntity.ok(optionalAccount.get()) : ResponseEntity.notFound().build();
	}
	
	public ResponseEntity<?> create(AccountDTO accountDTO) {
		Optional<Account> optionalAccount = accountRepository.findByDocumentNumber(accountDTO.getDocumentNumber());
		
		if (optionalAccount.isPresent()) return new ResponseEntity<>("Document exist", HttpStatus.BAD_REQUEST);
		Account account = new Account();
		account.setDocumentNumber(accountDTO.getDocumentNumber());
		return ResponseEntity.ok(accountRepository.save(account));
	}
}
