package com.br.pismo.teste.pismo.repository;

import com.br.pismo.teste.pismo.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
	
	
	Optional<Account> findByDocumentNumber(String documentNumber);
}
