package com.br.pismo.teste.pismo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "ACCOUNT")
public class Account {
	
	@Id
	@Column(name = "ACCOUNT_ID")
	private Long id;
	
	@Column(name = "DOCUMENT_NUMBER")
	private Long documentNumber;
}
