package com.taimur.training.backbase.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taimur.training.backbase.api.model.Bank;
import com.taimur.training.backbase.api.model.Transaction;
import com.taimur.training.backbase.services.OpenBankService;

@RestController
public class TransactionServiceController {

	Logger log = LoggerFactory.getLogger(TransactionServiceController.class);

	@Autowired
	private OpenBankService service;

	@GetMapping(path = "/transactions/{bank}", produces = "application/json")
	public String findAll(@PathVariable String bank)
			throws JsonProcessingException {
		System.out.println(bank);
		List<Transaction> transactions = service
				.findAllTransactionsForBank(bank);
		return new ObjectMapper().writeValueAsString(transactions);
	}
}
