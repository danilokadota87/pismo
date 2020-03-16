package com.br.pismo.teste.pismo.controller;


import com.br.pismo.teste.pismo.dto.TransactionDTO;
import com.br.pismo.teste.pismo.entity.Transaction;
import com.br.pismo.teste.pismo.service.TransactionService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/transactions")
public class TransactionController {
	
	private final TransactionService transactionService;
	
	@Autowired
	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}
	
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Transação criada"),
			@ApiResponse(code = 400, message = "Conta ou operação não existe"),
			@ApiResponse(code = 500, message = "Foi gerada uma exceção")
	})
	@PostMapping
	public ResponseEntity<?> defaultPost(@RequestBody TransactionDTO transactionDTO) {
		return transactionService.create(transactionDTO);
	}
	
	
}
