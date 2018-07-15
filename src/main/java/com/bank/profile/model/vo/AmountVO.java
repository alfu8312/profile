package com.bank.profile.model.vo;

import java.util.Date;

public abstract class AmountVO extends AbstractVO {

	private String accountNumber;
	private Integer amount;
	private Date createDt;
	
	public AmountVO(String accountNumber, Integer amount, Date createDt) {
		this.accountNumber = accountNumber;
		this.amount = amount;
		this.createDt = createDt;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

}
