package com.taimur.training.backbase.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

	@GetMapping(path = "/{bank}/transactions", produces = "application/json")
	public String findAllForBank(@PathVariable String bank)
			throws JsonProcessingException {
		List<Transaction> transactions = service
				.findAllTransactionsForBank(bank);
		return new ObjectMapper().writeValueAsString(transactions);
	}

	@GetMapping(path = "/{bank}/transactions/types/{type}", produces = "application/json")
	public String findByTransactionType(@PathVariable String bank,
			@PathVariable String type) throws JsonProcessingException {

		List<Transaction> transactions = service
				.findAllTransactionsForBank(bank);

		List<Transaction> filtered = transactions.stream()
				.filter(t -> t.getTransactionType().equalsIgnoreCase(type))
				.collect(Collectors.toList());
		return new ObjectMapper().writeValueAsString(filtered);
	}

	@GetMapping(path = "/{bank}/transactions/totals", produces = "application/json")
	public String findTotalsByType(@PathVariable String bank)
			throws JsonProcessingException {

		//List<Transaction> transactions = service
			//	.findAllTransactionsForBank(bank);
		
		// TODO: put this in a unit test
		Transaction t1 = new Transaction();
		Transaction t2 = new Transaction();
		Transaction t3 = new Transaction();
		Transaction t4 = new Transaction();
		Transaction t5 = new Transaction();
		
		t1.setTransactionType("SEPA");
		t1.setTransactionAmount(10.01);
		t2.setTransactionType("SEPA");
		t2.setTransactionAmount(25.30);
		t3.setTransactionType("Test");
		t3.setTransactionAmount(50.4);
		t4.setTransactionType("Test");
		t4.setTransactionAmount(5.5);
		t5.setTransactionType("peepee poopoo");
		t5.setTransactionAmount(0.01);
		
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(t1);
		transactions.add(t2);
		transactions.add(t3);
		transactions.add(t4);
		transactions.add(t5);

		// TODO: fix rounding/number format errors on this
		Map<String, Double> totals = transactions.stream()
				.collect(Collectors.groupingBy(Transaction::getTransactionType,
						Collectors
								.summingDouble(t -> t.getTransactionAmount())));
		
		return new ObjectMapper().writeValueAsString(totals);
	}

}
