package com.br.pismo.teste.pismo.response;

import com.br.pismo.teste.pismo.entity.OperationTypes;
import com.br.pismo.teste.pismo.entity.Transaction;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionResponse {
	
	
	private Long id;
	private AccountResponse accountResponse;
	private OperationTypes operationTypes;
	private BigDecimal amount;
	
	public TransactionResponse(Transaction transaction) {
		this.id = transaction.getId();
		this.accountResponse = new AccountResponse(transaction.getAccount());
		this.operationTypes = transaction.getOperationTypes();
		this.amount = transaction.getAmount();
		
	}
}
