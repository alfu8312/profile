package com.bank.profile.model.vo;

import java.util.Date;
import java.util.List;

import com.bank.profile.model.vo.mongo.MongoAmountVO;

public class AccountProfileVO {
	private String customerNumber;
	private String accountNumber;
	private Date createDt;
	private int balance;
	private List<MongoAmountVO> deposits;
	private List<MongoAmountVO> withdrawals;
	private List<MongoAmountVO> transfers;

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public List<MongoAmountVO> getDeposits() {
		return deposits;
	}

	public void setDeposits(List<MongoAmountVO> deposits) {
		this.deposits = deposits;
	}

	public List<MongoAmountVO> getWithdrawals() {
		return withdrawals;
	}

	public void setWithdrawals(List<MongoAmountVO> withdrawals) {
		this.withdrawals = withdrawals;
	}

	public List<MongoAmountVO> getTransfers() {
		return transfers;
	}

	public void setTransfers(List<MongoAmountVO> transfers) {
		this.transfers = transfers;
	}

}
