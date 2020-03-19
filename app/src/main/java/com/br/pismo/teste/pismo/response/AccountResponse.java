package com.br.pismo.teste.pismo.response;

import com.br.pismo.teste.pismo.entity.Account;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountResponse {
	
	
	private Long id;
	private String documentNumber;
	private BigDecimal availableCreditLimit;
	
	public AccountResponse(Account accountSave) {
		this.id = accountSave.getId();
		this.documentNumber = accountSave.getDocumentNumber();
		this.availableCreditLimit = accountSave.getAvailableCreditLimit();
	}
}
