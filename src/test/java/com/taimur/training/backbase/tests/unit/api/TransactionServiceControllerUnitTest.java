package com.taimur.training.backbase.tests.unit.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taimur.training.backbase.api.controller.TransactionServiceController;
import com.taimur.training.backbase.api.model.Transaction;
import com.taimur.training.backbase.services.TransactionService;
import com.taimur.training.backbase.tests.unit.mocks.MockTransactions;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceControllerUnitTest {

	@Mock
	private TransactionService service;

	@InjectMocks
	private TransactionServiceController rest;

	final String BANK = "ImagineBank";

	@Test
	public void testGetTransactions() throws JsonProcessingException {
		List<Transaction> body = MockTransactions
				.prepareMockTransactionsPopulated().stream()
				.filter(t -> t.getCounterpartyName().equals(BANK))
				.collect(Collectors.toList());

		System.out.println(body);

		given(service.findAllForBank(BANK)).willReturn(body);

		String responseBody = rest.findAllForBank(BANK);
		String reference = new ObjectMapper().writeValueAsString(body);

		assertFalse(responseBody == null || responseBody.isEmpty());
		assertEquals(responseBody, reference);
	}

	@Test
	public void testGetByType() throws JsonProcessingException {
		List<String> types = MockTransactions.prepareMockTypes();
		List<Transaction> allTransactionsForBank = MockTransactions
				.prepareMockTransactionsPopulated().stream()
				.filter(t -> t.getCounterpartyName().equals(BANK))
				.collect(Collectors.toList());

		for (String type : types) {
			List<Transaction> filtered = allTransactionsForBank.stream()
					.filter(t -> t.getTransactionType().equals(type))
					.collect(Collectors.toList());

			given(service.findByType(BANK, type)).willReturn(filtered);

			String responseBody = rest.findByTransactionType(BANK, type);
			String reference = new ObjectMapper().writeValueAsString(filtered);

			assertFalse(responseBody == null || responseBody.isEmpty());
			assertEquals(responseBody, reference);
		}
	}

	@Test
	public void testGetTotals() throws JsonProcessingException {
		List<Transaction> allTransactionsForBank = MockTransactions
				.prepareMockTransactionsPopulated().stream()
				.filter(t -> t.getCounterpartyName().equals(BANK))
				.collect(Collectors.toList());

		Map<String, Double> totals = allTransactionsForBank.stream()
				.collect(Collectors.groupingBy(Transaction::getTransactionType,
						Collectors
								.summingDouble(t -> t.getTransactionAmount())));
		
		given(service.findAmountsByType(BANK)).willReturn(totals);
		
		String responseBody = rest.findTotalsByType(BANK);
		String reference = new ObjectMapper().writeValueAsString(totals);
		
		assertFalse(responseBody == null || responseBody.isEmpty());
		assertEquals(responseBody, reference);
	}

}
