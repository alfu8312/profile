package com.bank.profile.model.dao;

import com.bank.profile.model.vo.AccountVO;

public interface AccountDao<T extends AccountVO> {
	public void saveAccount(T account);

	public T findByAccountId(int accountNumber);

	public void updateSummary(T account);
}
