package com.bank.profile.model.vo;

import java.util.Date;

public class CustomerProfileVO {
	private String customerNumber;
	private String name;
	private Date joinDt;
	private int largestDepositAmount;
	private int largestWithdrawAmount;
	private int largestTransferAmount;

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getJoinDt() {
		return joinDt;
	}

	public void setJoinDt(Date joinDt) {
		this.joinDt = joinDt;
	}

	public int getLargestDepositAmount() {
		return largestDepositAmount;
	}

	public void setLargestDepositAmount(int largestDepositAmount) {
		this.largestDepositAmount = largestDepositAmount;
	}

	public int getLargestWithdrawAmount() {
		return largestWithdrawAmount;
	}

	public void setLargestWithdrawAmount(int largestWithdrawAmount) {
		this.largestWithdrawAmount = largestWithdrawAmount;
	}

	public int getLargestTransferAmount() {
		return largestTransferAmount;
	}

	public void setLargestTransferAmount(int largestTransferAmount) {
		this.largestTransferAmount = largestTransferAmount;
	}

}
