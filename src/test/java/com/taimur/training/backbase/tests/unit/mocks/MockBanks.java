package com.taimur.training.backbase.tests.unit.mocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.taimur.training.backbase.api.model.Bank;

public class MockBanks {
	
	public static List<Bank> prepareMockBanksPopulated() {
		return new ArrayList<Bank>(Arrays.asList(new Bank("InspireBank"),
				new Bank("ImagineBank"), new Bank("UninspireBank")));
	}

	public static List<Bank> prepareMockBanksEmpty() {
		return new ArrayList<Bank>();
	}

}
