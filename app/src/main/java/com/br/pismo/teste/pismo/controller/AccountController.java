package com.br.pismo.teste.pismo.controller;


import com.br.pismo.teste.pismo.dto.AccountDTO;
import com.br.pismo.teste.pismo.entity.Account;
import com.br.pismo.teste.pismo.service.AccountService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/accounts")
public class AccountController {
	
	private final AccountService accountService;
	
	@Autowired
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Detalhe de Conta"),
			@ApiResponse(code = 404, message = "Conta não existe"),
			@ApiResponse(code = 500, message = "Foi gerada uma exceção")
	})
	@GetMapping("/{id}")
	public ResponseEntity<?> account(@PathVariable("id") Long id) {
		
		return accountService.get(id);
	}
	
	@GetMapping
	public List<Account> account() {
		
		return accountService.getAll();
	}
	
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Conta criada"),
			@ApiResponse(code = 400, message = "Document ja existe"),
			@ApiResponse(code = 500, message = "Foi gerada uma exceção")
	})
	@PostMapping
	public ResponseEntity<?> defaultPost(@RequestBody AccountDTO accountDTO) {
		return accountService.create(accountDTO);
	}
	
}
