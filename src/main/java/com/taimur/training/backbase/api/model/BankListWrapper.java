package com.taimur.training.backbase.api.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Wrapper class for deserializing lists of Banks from the Open Bank API
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BankListWrapper {

	private List<Bank> banks;

	@JsonCreator
	public BankListWrapper() {
		banks = new ArrayList<>();
	}

	public List<Bank> getBanks() {
		return banks;
	}

	@JsonSetter("banks")
	public void setBanks(List<Bank> banks) {
		this.banks = banks;
	}

}
