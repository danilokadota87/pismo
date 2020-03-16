package com.br.pismo.teste.pismo.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ACCOUNT")
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "ACCOUNT_ID")
	private Long id;
	
	@Column(name = "DOCUMENT_NUMBER")
	private Long documentNumber;
}
