package com.bank.profile.model.dao;

import java.util.List;

import com.bank.profile.model.vo.AccountVO;

public interface AccountDao<T extends AccountVO> {
	public void saveAccount(T account);

	public T findByAccountId(String accountNumber);

	public List<T> findByCustomerId(String customerNumber);

}
