package com.br.pismo.teste.pismo.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionDTO {
	
	
	private Long id;
	private Long accountId;
	private Long operationTypesId;
	private BigDecimal amount;
	
}
