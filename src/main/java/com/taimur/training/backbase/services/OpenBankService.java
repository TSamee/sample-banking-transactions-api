package com.taimur.training.backbase.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.ServletContextAware;

import com.taimur.training.backbase.api.controller.TransactionServiceController;
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
 */
@Service
public class OpenBankService implements ServletContextAware {

	private ServletContext context;
	private String baseUrl;
	private String version;
	private List<Transaction> transactions;
	private final String VIEW = "public";

	@Autowired
	private RestTemplate restTemplate;

	@PostConstruct
	public void init() {
		baseUrl = context.getInitParameter("openbank-url");
		version = context.getInitParameter("openbank-version");
	}

	public List<Transaction> findAllTransactions() {
		List<Transaction> transactions = new ArrayList<Transaction>();

		List<Bank> banks = getBanks();

		if (!banks.isEmpty()) {
			for (Bank bank : banks) {
				transactions.addAll(findAllTransactionsForBank(bank.getId()));
			};
		}
		return transactions;
	}

	public List<Transaction> findAllTransactionsForBank(String bankId) {

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

	@Override
	public void setServletContext(ServletContext servletContext) {
		context = servletContext;
	}

}
