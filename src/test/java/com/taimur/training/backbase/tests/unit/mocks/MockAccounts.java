package com.taimur.training.backbase.tests.unit.mocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.taimur.training.backbase.api.model.Account;

public class MockAccounts {
	public static List<Account> prepareMockAccounts(String bank) {

		switch (bank) {
		case "InspireBank":
			return new ArrayList<Account>(Arrays.asList(
					new Account("acc-002"), new Account("acc-004"),
					new Account("acc-005"), new Account("acc-006"),
					new Account("acc-007")));
		case "ImagineBank":
			return new ArrayList<Account>(Arrays.asList(new Account("acc-001")));

		case "UninspireBank":
			return new ArrayList<Account>(Arrays.asList(new Account("acc-003")));
		
		default:
			return new ArrayList<Account>();
		}

	}
}
