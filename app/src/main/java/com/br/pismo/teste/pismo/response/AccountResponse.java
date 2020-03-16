package com.br.pismo.teste.pismo.response;

import com.br.pismo.teste.pismo.entity.Account;
import lombok.Data;

@Data
public class AccountResponse {
	
	
	private Long id;
	private Long documentNumber;
	
	public AccountResponse(Account accountSave) {
		this.id = accountSave.getId();
		this.documentNumber = accountSave.getDocumentNumber();
	}
}
