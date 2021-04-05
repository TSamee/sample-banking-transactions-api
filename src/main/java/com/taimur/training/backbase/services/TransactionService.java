package com.taimur.training.backbase.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import com.taimur.training.backbase.api.model.Transaction;

@Service
public class TransactionService {

	private OpenBankTransactionRepo repo;

	/**
	 * @param repo Injected
	 */
	public TransactionService(OpenBankTransactionRepo repo) {
		this.repo = repo;
	}

	public List<Transaction> findAllForBank(String bank) {
		return repo.getAllTransactionsForBank(bank);
	}

	public List<Transaction> findAll() {
		return repo.getAllTransactions();
	}

	public List<Transaction> findByType(String bank, String type) {
		List<Transaction> transactions = repo.getAllTransactionsForBank(bank);
		List<Transaction> filtered = transactions.stream()
				.filter(t -> t.getTransactionType().equalsIgnoreCase(type))
				.collect(Collectors.toList());

		return filtered;
	}

	public Map<String, Double> findAmountsByType(String bank) {

		List<Transaction> transactions = repo.getAllTransactionsForBank(bank);

		// TODO: fix rounding/number format errors on this
		Map<String, Double> totals = transactions.stream()
				.collect(Collectors.groupingBy(Transaction::getTransactionType,
						Collectors
								.summingDouble(t -> t.getTransactionAmount())));

		return totals;
	}

}
