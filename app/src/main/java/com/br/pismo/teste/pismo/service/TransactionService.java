package com.br.pismo.teste.pismo.service;


import com.br.pismo.teste.pismo.dto.TransactionDTO;
import com.br.pismo.teste.pismo.entity.Account;
import com.br.pismo.teste.pismo.entity.OperationTypes;
import com.br.pismo.teste.pismo.entity.Transaction;
import com.br.pismo.teste.pismo.repository.AccountRepository;
import com.br.pismo.teste.pismo.repository.OperationTypeRepository;
import com.br.pismo.teste.pismo.repository.TransactionRepository;
import com.br.pismo.teste.pismo.response.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransactionService {
	
	private TransactionRepository transactionRepository;
	private AccountRepository accountRepository;
	private OperationTypeRepository operationTypeRepository;
	
	
	@Autowired
	private TransactionService(TransactionRepository transactionRepository,
	                           AccountRepository accountRepository,
	                           OperationTypeRepository operationTypeRepository) {
		this.transactionRepository = transactionRepository;
		this.accountRepository = accountRepository;
		this.operationTypeRepository = operationTypeRepository;
	}
	
	
	public ResponseEntity<?> create(TransactionDTO transactionDTO) {
		Optional<Account> optionalAccount = accountRepository.findById(transactionDTO.getAccountId());
		Optional<OperationTypes> operationTypes = operationTypeRepository.findById(transactionDTO.getOperationTypesId());
		if (!optionalAccount.isPresent()) return new ResponseEntity<>("Account Not Found", HttpStatus.NOT_FOUND);
		if (!operationTypes.isPresent()) return new ResponseEntity<>("Operation Type Not Found", HttpStatus.NOT_FOUND);
		
		Transaction transaction = new Transaction();
		transaction.setAccount(optionalAccount.get());
		transaction.setOperationTypes(operationTypes.get());
		transaction.setLocalDateTime(LocalDateTime.now());
		transaction.setAmount(operationTypes.get().isCreditType() ? transactionDTO.getAmount() : transactionDTO.getAmount().negate());
		transactionRepository.save(transaction);
		return ResponseEntity.ok(new TransactionResponse(transaction));
	}
	
}
