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

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private OperationTypeRepository operationTypeRepository;
	@Autowired
	private AccountService accountService;
	
	
	@Transactional
	public ResponseEntity<?> create(TransactionDTO transactionDTO) {
		Optional<Account> optionalAccount = accountRepository.findById(transactionDTO.getAccountId());
		Optional<OperationTypes> operationTypes = operationTypeRepository.findById(transactionDTO.getOperationTypesId());
		if (!optionalAccount.isPresent()) return new ResponseEntity<>("Account Not Found", HttpStatus.NOT_FOUND);
		if (!operationTypes.isPresent()) return new ResponseEntity<>("Operation Type Not Found", HttpStatus.NOT_FOUND);
		
		if (accountService.validityAccountDontHaveCreditToTransaction(transactionDTO.getAccountId(), transactionDTO.getAmount(), operationTypes.get().isCreditType())) {
			return new ResponseEntity<>("Dont have credit to this transaction", HttpStatus.BAD_REQUEST);
		}
		
		Transaction transaction = new Transaction();
		transaction.setAccount(optionalAccount.get());
		transaction.setOperationTypes(operationTypes.get());
		transaction.setLocalDateTime(LocalDateTime.now());
		transaction.setAmount(operationTypes.get().isCreditType() ? transactionDTO.getAmount() : transactionDTO.getAmount().negate());
		
		transactionRepository.save(transaction);
		
		
		return ResponseEntity.ok(new TransactionResponse(transaction));
	}
	
}
