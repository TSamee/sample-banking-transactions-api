package com.taimur.training.backbase.tests.unit.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.taimur.training.backbase.api.model.Transaction;
import com.taimur.training.backbase.services.OpenBankTransactionRepo;
import com.taimur.training.backbase.services.TransactionService;
import com.taimur.training.backbase.tests.unit.mocks.MockTransactions;

/**
 * Unit tests for TransactionService. Note testing 'find by bank' operations is
 * not meaningful since the application does not maintain a Transaction-Bank
 * mapping
 */
@ExtendWith(MockitoExtension.class)
public class TransactionServiceUnitTest {

	@Mock
	private OpenBankTransactionRepo repo;

	@InjectMocks
	private TransactionService service;

	@Test
	public void transactionServicePresent() {
		assertEquals(
				"class com.taimur.training.backbase.services.TransactionService",
				this.service.getClass().toString());
	}

	@Test
	public void findAllSizeMatch() {

		List<Transaction> mockTransactionsPopulated = MockTransactions.prepareMockTransactionsPopulated();
		List<Transaction> mockTransactionsEmpty = MockTransactions.prepareMockTransactionsEmpty();

		// given
		given(repo.getAllTransactions()).willReturn(mockTransactionsPopulated);

		// when
		List<Transaction> returnedList = service.findAll();

		assertEquals(returnedList.size(), mockTransactionsPopulated.size());

		given(repo.getAllTransactions()).willReturn(mockTransactionsEmpty);
		List<Transaction> emptyReturnedList = service.findAll();
		assertEquals(emptyReturnedList.size(), mockTransactionsEmpty.size());

	}

	@Test
	public void findAllContentsMatchPopulated() {
		List<Transaction> mockTransactionsPopulated = MockTransactions.prepareMockTransactionsPopulated();

		// given
		given(repo.getAllTransactions()).willReturn(mockTransactionsPopulated);
		List<Transaction> returnedList = service.findAll();

		for (int i = 0; i < returnedList.size(); i++) {
			assertEquals(returnedList.get(i), mockTransactionsPopulated.get(i));
		}
	}

	@Test
	public void findByBankSizeAndContentsMatch() {
		List<Transaction> mockTransactionsPopulated = MockTransactions.prepareMockTransactionsPopulated();

		// given
		given(repo.getAllTransactionsForBank(""))
				.willReturn(mockTransactionsPopulated);

		List<Transaction> returnedList = service.findAllForBank("");
		assertEquals(returnedList.size(), mockTransactionsPopulated.size());
		for (int i = 0; i < returnedList.size(); i++) {
			assertEquals(returnedList.get(i), mockTransactionsPopulated.get(i));
		}
	}

	@Test
	public void filterByType() {
		List<String> types = MockTransactions.prepareMockTypes();
		List<Transaction> mockTransactionsPopulated = MockTransactions.prepareMockTransactionsPopulated();

		given(repo.getAllTransactionsForBank(""))
				.willReturn(mockTransactionsPopulated);

		for (String type : types) {
			List<Transaction> filtered = service.findByType("", type);
			for (Transaction transaction : filtered) {
				assertEquals(transaction.getTransactionType(), type);
			}
		}
	}

	@Test
	public void checkAmountsByType() {
		List<Transaction> mockTransactionsPopulated = MockTransactions.prepareMockTransactionsPopulated();

		Map<String, Double> reference = new HashMap<String, Double>();
		for (Transaction transaction : mockTransactionsPopulated) {
			if (reference.containsKey(transaction.getTransactionType())) {
				Double oldTotal = reference
						.get(transaction.getTransactionType());
				reference.put(transaction.getTransactionType(),
						oldTotal + transaction.getTransactionAmount());
			} else {
				reference.put(transaction.getTransactionType(),
						transaction.getTransactionAmount());
			}
		}

		given(repo.getAllTransactionsForBank(""))
				.willReturn(mockTransactionsPopulated);
		Map<String, Double> testMap = service.findAmountsByType("");

		assertEquals(testMap, reference);
	}

}
