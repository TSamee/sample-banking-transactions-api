package com.taimur.training.backbase.tests.unit.models;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.taimur.training.backbase.api.model.Transaction;
import com.taimur.training.backbase.tests.unit.mocks.MockTransactions;

public class TransactionUnitTest {

	@Test
	public void testSettersAndGetters() {
		final String ID = "078ah4-test";
		final String ACCOUNTID = "acc-001";
		final String COUNTERPARTYACCOUNT = "counter-001";
		final String COUNTERPARTYNAME = "ImagineBank";
		final String COUNTERPARTYLOGOPATH = "site/assets/logo-1";
		final Double INSTRUCTEDAMOUNT = 300.50;
		final String INSTRUCTEDCURRENCY = "USD";
		final Double TRANSACTIONAMOUNT = 300.50;
		final String TRANSACTIONCURRENCY = "USD";
		final String TRANSACTIONTYPE = "Deposit";
		final String DESCRIPTION = "Savings Deposit";
		
		Transaction test = MockTransactions.getSingleTransaction();
		assertEquals(test.getId(), ID);
		assertEquals(test.getAccountId(), ACCOUNTID);
		assertEquals(test.getCounterpartyAccount(), COUNTERPARTYACCOUNT);
		assertEquals(test.getCounterpartyName(), COUNTERPARTYNAME);
		assertEquals(test.getCounterpartyLogoPath(), COUNTERPARTYLOGOPATH);
		assertEquals(test.getInstructedAmount(), INSTRUCTEDAMOUNT);
		assertEquals(test.getInstructedCurrency(), INSTRUCTEDCURRENCY);
		assertEquals(test.getTransactionAmount(), TRANSACTIONAMOUNT);
		assertEquals(test.getTransactionCurrency(), TRANSACTIONCURRENCY);
		assertEquals(test.getTransactionType(), TRANSACTIONTYPE);
		assertEquals(test.getDescription(), DESCRIPTION);
		
	}
	
	@Test
	public void testEquals() {
		List<Transaction> list = MockTransactions.prepareMockTransactionsPopulated();
		Transaction equalToFirst = MockTransactions.getSingleTransaction();
		
		assertTrue(list.get(0).equals(equalToFirst));
		assertFalse(list.get(0).equals(list.get(1)));
		assertFalse(list.get(0).equals(null));
	}
	
	@Test
	public void testHashCode() {
		Transaction t1 = MockTransactions.prepareMockTransactionsPopulated().get(0);
		Transaction t2 = MockTransactions.prepareMockTransactionsPopulated().get(0);
		
		assertTrue(t1.hashCode() == t2.hashCode());
	}
	
}
