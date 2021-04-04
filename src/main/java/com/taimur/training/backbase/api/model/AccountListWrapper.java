package com.taimur.training.backbase.api.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
/**
 * Wrapper class for deserializing lists of Accounts from the Open Bank API
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountListWrapper {

	private List<Account> accounts;

	@JsonCreator
	public AccountListWrapper() {
		accounts = new ArrayList<>();
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	@JsonSetter("accounts")
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

}