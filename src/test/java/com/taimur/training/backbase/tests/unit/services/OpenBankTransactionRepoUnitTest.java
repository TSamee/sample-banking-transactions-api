package com.taimur.training.backbase.tests.unit.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.servlet.ServletContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.taimur.training.backbase.api.model.Account;
import com.taimur.training.backbase.api.model.AccountListWrapper;
import com.taimur.training.backbase.api.model.BankListWrapper;
import com.taimur.training.backbase.api.model.Transaction;
import com.taimur.training.backbase.api.model.TransactionListWrapper;
import com.taimur.training.backbase.services.OpenBankTransactionRepo;
import com.taimur.training.backbase.tests.unit.mocks.MockAccounts;
import com.taimur.training.backbase.tests.unit.mocks.MockBanks;
import com.taimur.training.backbase.tests.unit.mocks.MockTransactions;

/**
 * Unit tests for OpenBankTransactionRepo. Note testing 'find by bank'
 * operations is not meaningful since the application does not maintain a
 * Transaction-Bank mapping
 */

@ExtendWith(MockitoExtension.class)
public class OpenBankTransactionRepoUnitTest {

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private OpenBankTransactionRepo repo;

	@AfterEach
	public void resetMockRest() {
		reset(restTemplate);
	}

	@Test
	public void repoPresent() {
		assertEquals(
				"class com.taimur.training.backbase.services.OpenBankTransactionRepo",
				this.repo.getClass().toString());
	}

	@Test
	public void findForBankDataPresent() {

		BankListWrapper mockBankWrapper = new BankListWrapper();
		mockBankWrapper.setBanks(MockBanks.prepareMockBanksPopulated());

		TransactionListWrapper mockTransactionWrapper = new TransactionListWrapper();

		mockTransactionWrapper.setTransactions(MockTransactions
				.prepareMockTransactionsPopulated().stream()
				.filter(t -> t.getCounterpartyName().equals("InspireBank"))
				.collect(Collectors.toList()));

		initMockRest(mockBankWrapper, mockTransactionWrapper);

		List<Transaction> transactions = repo
				.getAllTransactionsForBank("InspireBank");

		assertTrue(transactions.size() > 0);
		assertEquals(transactions.size(),
				mockTransactionWrapper.getTransactions().size());
	}

	@Test
	public void findAllDataPresent() {
		BankListWrapper mockBankWrapper = new BankListWrapper();
		TransactionListWrapper mockTransactionWrapper = new TransactionListWrapper();

		mockBankWrapper.setBanks(MockBanks.prepareMockBanksPopulated());
		mockTransactionWrapper.setTransactions(
				MockTransactions.prepareMockTransactionsPopulated());

		initMockRest(mockBankWrapper, mockTransactionWrapper);

		List<Transaction> transactions = repo.getAllTransactions();

		assertTrue(transactions.size() > 0);
		assertEquals(transactions.size(),
				mockTransactionWrapper.getTransactions().size());
	}

	@Test
	public void emptyBanks() {
		BankListWrapper mockBankWrapper = new BankListWrapper();
		mockBankWrapper.setBanks(MockBanks.prepareMockBanksEmpty());

		TransactionListWrapper mockTransactionWrapper = new TransactionListWrapper();
		mockTransactionWrapper.setTransactions(
				MockTransactions.prepareMockTransactionsPopulated());

		initMockRest(mockBankWrapper, mockTransactionWrapper);

		List<Transaction> transactions = repo.getAllTransactions();

		assertTrue(transactions.size() == 0);
	}

	@Test
	public void testRestClientException() {
		when(restTemplate.getForObject(anyString(), eq(BankListWrapper.class)))
				.thenThrow(RestClientException.class);

		List<Transaction> result = repo.getAllTransactions();
		assertTrue(result.size() == 0);
	}

	private void initMockRest(BankListWrapper mockBankWrapper,
			TransactionListWrapper mockTransactionWrapper) {

		// given
		when(restTemplate.getForObject(anyString(), any(Class.class)))
				.thenAnswer(invocationOnMock -> {
					String arg1 = (String) invocationOnMock.getArgument(0);
					if (arg1.contains("transaction")) {
						String accountNumber = arg1.substring(
								arg1.indexOf("accounts/")
										+ "accounts/".length(),
								arg1.indexOf("/public"));
						TransactionListWrapper temp = new TransactionListWrapper();
						List<Transaction> tempList = mockTransactionWrapper
								.getTransactions().stream()
								.filter(t -> t.getAccountId()
										.equals(accountNumber))
								.collect(Collectors.toList());
						temp.setTransactions(tempList);
						return temp;
					} else if (arg1.contains("accounts")) {
						String bankId = arg1.substring(
								arg1.indexOf("banks/") + "banks/".length(),
								arg1.indexOf("/accounts"));
						AccountListWrapper temp = new AccountListWrapper();
						List<Account> tempList = MockAccounts
								.prepareMockAccounts(bankId);
						temp.setAccounts(tempList);
						return temp;
					} else if (arg1.contains("banks")) {
						return mockBankWrapper;
					}
					return invocationOnMock;
				});
	}

}
