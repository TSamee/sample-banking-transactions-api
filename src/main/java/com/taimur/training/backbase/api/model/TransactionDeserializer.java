package com.taimur.training.backbase.api.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * Custom deserializer for unmarshaling Open Bank transaction data into the
 * Backbase API format
 */
public class TransactionDeserializer extends StdDeserializer<Transaction> {

	public TransactionDeserializer() {
		this(null);
	}

	public TransactionDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public Transaction deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		Transaction res = new Transaction();
		JsonNode root = jp.getCodec().readTree(jp);

		String id = root.get("id").asText();
		String accountId = root.path("this_account").get("id").asText();
		String counterpartyAccount = root.path("other_account").get("number")
				.asText();
		String counterpartyName = root.path("other_account").path("holder")
				.get("name").asText();
		String counterPartyLogoPath = root.path("other_account").path("metadata").get("image_URL").asText();
		Double instructedAmount = root.path("details").path("value").get("amount").asDouble();
		String instructedCurrency = root.path("details").path("value").get("currency").asText();
		Double transactionAmount = instructedAmount;
		String transactionCurrency = instructedCurrency;
		String transactionType = root.path("details").get("type").asText();
		String description = root.path("details").get("description").asText();

		res.setId(id);
		res.setAccountId(accountId);
		res.setCounterpartyAccount(counterpartyAccount);
		res.setCounterpartyName(counterpartyName);
		res.setCounterpartyLogoPath(counterPartyLogoPath);
		res.setInstructedAmount(instructedAmount);
		res.setInstructedCurrency(instructedCurrency);
		res.setTransactionAmount(transactionAmount);
		res.setTransactionCurrency(transactionCurrency);
		res.setTransactionType(transactionType);
		res.setDescription(description);
		
		return res;
	}

}
