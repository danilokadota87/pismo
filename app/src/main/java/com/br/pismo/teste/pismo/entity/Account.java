package com.br.pismo.teste.pismo.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "ACCOUNT")
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "ACCOUNT_ID")
	private Long id;
	
	@Column(name = "DOCUMENT_NUMBER")
	private String documentNumber;
	
	@Column(name = "AVAILABLE_CREDIT_LIMIT",nullable = false)
	private BigDecimal availableCreditLimit;
}
