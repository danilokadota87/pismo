package com.br.pismo.teste.pismo.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "TRANSACTION")
public class Transaction {
	
	@Id
	@Column(name = "TRANSACTION_ID")
	private Long id;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "ACCOUNT_ID")
	private Account account;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "OPERATION_TYPE_ID")
	private OperationTypes operationTypes;
	
	@Column(name = "AMOUNT")
	private BigDecimal amount;
	
	@Column(name = "EVENT_DATE")
	private LocalDateTime localDateTime;
	
}
