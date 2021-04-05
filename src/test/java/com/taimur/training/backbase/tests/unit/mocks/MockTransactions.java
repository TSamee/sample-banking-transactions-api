package com.taimur.training.backbase.tests.unit.mocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.taimur.training.backbase.api.model.Transaction;
import com.taimur.training.backbase.api.model.TransactionListWrapper;

public class MockTransactions {

	public static Transaction getSingleTransaction() {
		return MockTransactions.getFullMockTransaction("078ah4-test",
				"acc-001", "counter-001", "ImagineBank", "site/assets/logo-1",
				300.50, "USD", 300.50, "USD", "Deposit", "Savings Deposit");
	}
	
	public static List<Transaction> prepareMockTransactionsPopulated() {
		// Mock data preparation
		Transaction t1 = MockTransactions.getFullMockTransaction("078ah4-test",
				"acc-001", "counter-001", "ImagineBank", "site/assets/logo-1",
				300.50, "USD", 300.50, "USD", "Deposit", "Savings Deposit");
		Transaction t2 = MockTransactions.getFullMockTransaction("12345-test",
				"acc-002", "counter-002", "InspireBank", "site/assets/logo-2",
				51.23, "EUR", 51.23, "EUR", "Retirement", "Retirement Plan");
		Transaction t3 = MockTransactions.getFullMockTransaction("a25478-test",
				"acc-003", "counter-003", "UninspireBank", "site/assets/logo-3",
				10000.56, "JPY", 10000.56, "JPY", "Wire", "Wire Transfer");
		Transaction t4 = MockTransactions.getFullMockTransaction("42462-test",
				"acc-004", "counter-004", "InspireBank", "site/assets/logo-2",
				400.23, "EUR", 400.23, "EUR", "Retirement", "Retirement Plan");
		Transaction t5 = MockTransactions.getFullMockTransaction("001424-test",
				"acc-005", "counter-005", "InspireBank", "site/assets/logo-2",
				30.24, "EUR", 30.24, "EUR", "Deposit", "Savings Deposit");
		Transaction t6 = MockTransactions.getFullMockTransaction("133840-test",
				"acc-006", "counter-006", "InspireBank", "site/assets/logo-2",
				10.25, "EUR", 10.25, "EUR", "Deposit", "Savings Deposit");
		Transaction t7 = MockTransactions.getFullMockTransaction("133841-test",
				"acc-007", "counter-007", "InspireBank", "site/assets/logo-2",
				80.75, "EUR", 80.75, "EUR", "Wire", "Savings Deposit");

		return new ArrayList<Transaction>(
				Arrays.asList(t1, t2, t3, t4, t5, t6, t7));
	}

	public static List<String> prepareMockTypes() {
		return new ArrayList<String>(
				Arrays.asList("Retirement", "Deposit", "Wire"));
	}

	public static List<Transaction> prepareMockTransactionsEmpty() {
		return new ArrayList<Transaction>();
	}

	private static Transaction getFullMockTransaction(String id,
			String accountId, String counterpartyAccount,
			String counterpartyName, String counterpartyLogoPath,
			Double instructedAmount, String instructedCurrency,
			Double transactionAmount, String transactionCurrency,
			String transactionType, String description) {
		Transaction t = new Transaction();
		t.setId(id);
		t.setAccountId(accountId);
		t.setCounterpartyAccount(counterpartyAccount);
		t.setCounterpartyName(counterpartyName);
		t.setCounterpartyLogoPath(counterpartyLogoPath);
		t.setInstructedAmount(instructedAmount);
		t.setInstructedCurrency(instructedCurrency);
		t.setTransactionAmount(transactionAmount);
		t.setTransactionCurrency(transactionCurrency);
		t.setTransactionType(transactionType);
		t.setDescription(description);

		return t;
	}
}
