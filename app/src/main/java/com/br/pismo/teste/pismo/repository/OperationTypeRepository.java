package com.br.pismo.teste.pismo.repository;

import com.br.pismo.teste.pismo.entity.OperationTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationTypeRepository extends JpaRepository<OperationTypes, Long> {
	
	
}
