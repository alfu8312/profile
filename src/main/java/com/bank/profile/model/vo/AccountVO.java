package com.bank.profile.model.vo;

import java.util.Date;

public abstract class AccountVO extends AbstractVO {

	private int accountNumber;
	private int customerNumber;
	private int balance;
	private Date createDt;

	private int maxDeposit;
	private int maxWithdraw;
	private int maxTransfer;

	public int getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(int customerNumber) {
		this.customerNumber = customerNumber;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public int getMaxDeposit() {
		return maxDeposit;
	}

	public void setMaxDeposit(int maxDeposit) {
		this.maxDeposit = maxDeposit;
	}

	public int getMaxWithdraw() {
		return maxWithdraw;
	}

	public void setMaxWithdraw(int maxWithdraw) {
		this.maxWithdraw = maxWithdraw;
	}

	public int getMaxTransfer() {
		return maxTransfer;
	}

	public void setMaxTransfer(int maxTransfer) {
		this.maxTransfer = maxTransfer;
	}

}
