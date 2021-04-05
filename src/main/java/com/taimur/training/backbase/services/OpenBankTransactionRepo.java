package com.taimur.training.backbase.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.taimur.training.backbase.api.model.Account;
import com.taimur.training.backbase.api.model.AccountListWrapper;
import com.taimur.training.backbase.api.model.Bank;
import com.taimur.training.backbase.api.model.BankListWrapper;
import com.taimur.training.backbase.api.model.Transaction;
import com.taimur.training.backbase.api.model.TransactionListWrapper;

/**
 * Handles interaction with the Open Bank API, either Production or Sandbox as
 * configured in the servlet context variables 'openbank-url' and
 * 'openbank-version'. Handles deserialization of API responses into model
 * objects.
 * 
 * Note this service only interacts with public API views--authentication with
 * Open Bank is not supported.
 * 
 * TODO: implement caching for responses, as every get...() produces many API
 * calls
 */
@Repository
public class OpenBankTransactionRepo {

	private RestTemplate restTemplate;
	private List<Transaction> transactions;
	private final String VIEW = "public";
	private final String baseUrl = "https://apisandbox.openbankproject.com/obp";
	private final String version = "v1.2.1";

	public OpenBankTransactionRepo(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Cacheable({ "transactions" })
	public List<Transaction> getAllTransactions() {
		List<Transaction> transactions = new ArrayList<Transaction>();

		List<Bank> banks = getBanks();

		if (!banks.isEmpty()) {
			for (Bank bank : banks) {
				transactions.addAll(getAllTransactionsForBank(bank.getId()));
			}
			;
		}
		return transactions;
	}

	@Cacheable({ "transactions" })
	public List<Transaction> getAllTransactionsForBank(String bankId) {

		List<Transaction> transactions = new ArrayList<Transaction>();
		List<Account> accounts = getAccounts(bankId);
		if (!accounts.isEmpty()) {
			for (Account acc : accounts) {
				String accountId = acc.getId();
				List<Transaction> temp = getTransactions(bankId, accountId);
				if (!temp.isEmpty()) {
					for (Transaction trans : temp) {
						transactions.add(trans);
					}
				}
			}
		}
		return transactions;
	}

	@Scheduled(cron = "*/3 * * * *")
	@CacheEvict(value="transactions", allEntries=true)	
	public void clearCaches() {

	}

	private List<Transaction> getTransactions(String bankId, String accountId) {
		final String transactionsUrl = baseUrl + "/" + version + "/banks/"
				+ bankId + "/accounts/" + accountId + "/" + VIEW
				+ "/transactions";
		try {
			TransactionListWrapper wrapper = restTemplate.getForObject(
					transactionsUrl, TransactionListWrapper.class);
			return wrapper.getTransactions();
		} catch (RestClientException e) {
			return new ArrayList<Transaction>();
		}
	}

	private List<Account> getAccounts(String bankId) {
		final String accountsUrl = baseUrl + "/" + version + "/banks/" + bankId
				+ "/accounts/" + VIEW;
		try {
			AccountListWrapper wrapper = restTemplate.getForObject(accountsUrl,
					AccountListWrapper.class);
			return wrapper.getAccounts();

		} catch (RestClientException e) {
			return new ArrayList<Account>();
		}
	}

	private List<Bank> getBanks() {
		final String banksUrl = baseUrl + "/" + version + "/" + "banks";

		try {
			BankListWrapper wrapper = restTemplate.getForObject(banksUrl,
					BankListWrapper.class);
			return wrapper.getBanks();

		} catch (RestClientException e) {
			return new ArrayList<Bank>();
		}

	}

}
