package com.taimur.training.backbase.api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.taimur.training.backbase.services.TransactionDeserializer;

/**
 * POJO for storing Open Bank transaction details in Backbase API format
 */
@JsonDeserialize(using = TransactionDeserializer.class)
public class Transaction {

	private String id;
	private String accountId;
	private String counterpartyAccount;
	private String counterpartyName;
	private String counterpartyLogoPath;
	private Double instructedAmount;
	private String instructedCurrency;
	private Double transactionAmount;
	private String transactionCurrency;
	private String transactionType;
	private String description;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getCounterpartyAccount() {
		return counterpartyAccount;
	}
	public void setCounterpartyAccount(String counterpartyAccount) {
		this.counterpartyAccount = counterpartyAccount;
	}
	public String getCounterpartyName() {
		return counterpartyName;
	}
	public void setCounterpartyName(String counterpartyName) {
		this.counterpartyName = counterpartyName;
	}
	public String getCounterpartyLogoPath() {
		return counterpartyLogoPath;
	}
	public void setCounterpartyLogoPath(String counterpartyLogoPath) {
		this.counterpartyLogoPath = counterpartyLogoPath;
	}
	public Double getInstructedAmount() {
		return instructedAmount;
	}
	public void setInstructedAmount(Double instructedAmount) {
		this.instructedAmount = instructedAmount;
	}
	public String getInstructedCurrency() {
		return instructedCurrency;
	}
	public void setInstructedCurrency(String instructedCurrency) {
		this.instructedCurrency = instructedCurrency;
	}
	public Double getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(Double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public String getTransactionCurrency() {
		return transactionCurrency;
	}
	public void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "Transaction [id=" + id + ", accountId=" + accountId
				+ ", counterpartyAccount=" + counterpartyAccount
				+ ", counterpartyName=" + counterpartyName
				+ ", counterpartyLogoPath=" + counterpartyLogoPath
				+ ", instructedAmount=" + instructedAmount
				+ ", instructedCurrency=" + instructedCurrency
				+ ", transactionAmount=" + transactionAmount
				+ ", transactionCurrency=" + transactionCurrency
				+ ", transactionType=" + transactionType + ", description="
				+ description + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accountId == null) ? 0 : accountId.hashCode());
		result = prime * result + ((counterpartyAccount == null) ? 0
				: counterpartyAccount.hashCode());
		result = prime * result + ((counterpartyLogoPath == null) ? 0
				: counterpartyLogoPath.hashCode());
		result = prime * result + ((counterpartyName == null) ? 0
				: counterpartyName.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((instructedAmount == null) ? 0
				: instructedAmount.hashCode());
		result = prime * result + ((instructedCurrency == null) ? 0
				: instructedCurrency.hashCode());
		result = prime * result + ((transactionAmount == null) ? 0
				: transactionAmount.hashCode());
		result = prime * result + ((transactionCurrency == null) ? 0
				: transactionCurrency.hashCode());
		result = prime * result
				+ ((transactionType == null) ? 0 : transactionType.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (accountId == null) {
			if (other.accountId != null)
				return false;
		} else if (!accountId.equals(other.accountId))
			return false;
		if (counterpartyAccount == null) {
			if (other.counterpartyAccount != null)
				return false;
		} else if (!counterpartyAccount.equals(other.counterpartyAccount))
			return false;
		if (counterpartyLogoPath == null) {
			if (other.counterpartyLogoPath != null)
				return false;
		} else if (!counterpartyLogoPath.equals(other.counterpartyLogoPath))
			return false;
		if (counterpartyName == null) {
			if (other.counterpartyName != null)
				return false;
		} else if (!counterpartyName.equals(other.counterpartyName))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (instructedAmount == null) {
			if (other.instructedAmount != null)
				return false;
		} else if (!instructedAmount.equals(other.instructedAmount))
			return false;
		if (instructedCurrency == null) {
			if (other.instructedCurrency != null)
				return false;
		} else if (!instructedCurrency.equals(other.instructedCurrency))
			return false;
		if (transactionAmount == null) {
			if (other.transactionAmount != null)
				return false;
		} else if (!transactionAmount.equals(other.transactionAmount))
			return false;
		if (transactionCurrency == null) {
			if (other.transactionCurrency != null)
				return false;
		} else if (!transactionCurrency.equals(other.transactionCurrency))
			return false;
		if (transactionType == null) {
			if (other.transactionType != null)
				return false;
		} else if (!transactionType.equals(other.transactionType))
			return false;
		return true;
	}
}
