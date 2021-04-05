package com.taimur.training.backbase.tests.unit.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taimur.training.backbase.api.model.Transaction;

public class TransactionDeserializerUnitTest {

	@Test
	public void testDeserializeItem() throws JsonMappingException, JsonProcessingException {
		
		final String ID = "58aeed54-7042-456d-af86-f517bff5b7af";
		final String ACCOUNTID = "savings-kids-john";
		final String COUNTERPARTYACCOUNT = "savings-kids-john";
		final String COUNTERPARTYNAME = "ALIAS_03C57D";
		final String COUNTERPARTYLOGOPATH = "null";
		final Double INSTRUCTEDAMOUNT = 8.6;
		final String INSTRUCTEDCURRENCY = "GBP";
		final Double TRANSACTIONAMOUNT = 8.6;
		final String TRANSACTIONCURRENCY = "GBP";
		final String TRANSACTIONTYPE = "SEPA";
		final String DESCRIPTION = "This is a SEPA Transaction Request";
		
		String json = "{\"id\":\"58aeed54-7042-456d-af86-f517bff5b7af\",\"this_account\":{\"id\":\"savings-kids-john\",\"holders\":[{\"name\":\"Savings - Kids John\",\"is_alias\":false}],\"number\":\"832425-00304050\",\"kind\":\"savings\",\"IBAN\":null,\"swift_bic\":null,\"bank\":{\"national_identifier\":\"rbs\",\"name\":\"The Royal Bank of Scotland\"}},\"other_account\":{\"id\":\"5780MRN4uBIgWYmWAhI3pdqbSpItvOw4culXP5FWHJA\",\"holder\":{\"name\":\"ALIAS_03C57D\",\"is_alias\":true},\"number\":\"savings-kids-john\",\"kind\":\"AC\",\"IBAN\":\"4930396\",\"swift_bic\":null,\"bank\":{\"national_identifier\":null,\"name\":\"rbs\"},\"metadata\":{\"public_alias\":null,\"private_alias\":null,\"more_info\":null,\"URL\":null,\"image_URL\":null,\"open_corporates_URL\":null,\"corporate_location\":null,\"physical_location\":null}},\"details\":{\"type\":\"SEPA\",\"description\":\"This is a SEPA Transaction Request\",\"posted\":\"2020-06-05T08:28:38Z\",\"completed\":\"2020-06-05T08:28:38Z\",\"new_balance\":{\"currency\":\"GBP\",\"amount\":null},\"value\":{\"currency\":\"GBP\",\"amount\":\"8.60\"}},\"metadata\":{\"narrative\":null,\"comments\":[],\"tags\":[],\"images\":[],\"where\":null}}";
		Transaction test = new ObjectMapper().readValue(json, Transaction.class);
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
}
