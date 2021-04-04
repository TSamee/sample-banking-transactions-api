package com.taimur.training.backbase.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * POJO for storing Open Bank bank details (currently only IDs)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Bank {

	private String id;

	@JsonCreator
	public Bank(@JsonProperty("id") String id) {
		this.id = id;
	}

	@JsonProperty("id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
