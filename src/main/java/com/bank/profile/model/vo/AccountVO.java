package com.bank.profile.model.vo;

import java.util.Date;

public abstract class AccountVO extends AbstractVO {

	private String accountNumber;
	private String customerNumber;
	private Date createDt;

	private int balance = 0;
	private int maxDeposit = 0;
	private int maxWithdraw = 0;
	private int maxTransfer = 0;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
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
