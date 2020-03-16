package com.br.pismo.teste.pismo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "OPERATION_TYPE")
public class OperationTypes {
	
	@Id
	@Column(name = "OPERATION_TYPE_ID")
	private Long id;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "CREDIT_TYPE")
	private Boolean creditType;
	
	public Boolean isCreditType() {
		return creditType;
	}
	
}
