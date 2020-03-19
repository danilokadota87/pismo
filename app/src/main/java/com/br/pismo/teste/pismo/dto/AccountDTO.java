package com.br.pismo.teste.pismo.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDTO {
	
	
	private String documentNumber;
	private BigDecimal availableCreditLimit;
}
