package com.taimur.training.backbase.api.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taimur.training.backbase.api.model.Transaction;
import com.taimur.training.backbase.services.TransactionService;

/**
 * Controller handling requests to the REST API. 
 * @param bank requires the request to be prepended with /{bank}/
 * @throws JsonProcessingException -- calls Jackson ObjectMapper directly for marshaling
 */

@RestController
public class TransactionServiceController {

	Logger log = LoggerFactory.getLogger(TransactionServiceController.class);

	private TransactionService service;

	/**
	 * @param service Injected
	 */
	public TransactionServiceController(TransactionService service) {
		this.service = service;
	}

	@GetMapping(path = "/{bank}/transactions", produces = "application/json")
	public String findAllForBank(@PathVariable String bank)
			throws JsonProcessingException {
		List<Transaction> transactions = service
				.findAllForBank(bank);
		return new ObjectMapper().writeValueAsString(transactions);
	}

	@GetMapping(path = "/{bank}/transactions/types/{type}", produces = "application/json")
	public String findByTransactionType(@PathVariable String bank,
			@PathVariable String type) throws JsonProcessingException {

		List<Transaction> filtered = service.findByType(bank, type);
		return new ObjectMapper().writeValueAsString(filtered);
	}

	@GetMapping(path = "/{bank}/transactions/totals", produces = "application/json")
	public String findTotalsByType(@PathVariable String bank)
			throws JsonProcessingException {

		Map<String, Double> totals = service.findAmountsByType(bank);

		return new ObjectMapper().writeValueAsString(totals);
	}

}
