package com.taimur.training.backbase.api.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Wrapper class for deserializing lists of Transactions from the Open Bank API
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionListWrapper {

	private List<Transaction> transactions;

	@JsonCreator
	public TransactionListWrapper() {
		transactions = new ArrayList<>();
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	@JsonSetter("transactions")
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

}