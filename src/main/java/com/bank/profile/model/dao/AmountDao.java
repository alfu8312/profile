package com.bank.profile.model.dao;

import java.util.List;

import com.bank.profile.model.vo.AmountVO;

public interface AmountDao<T extends AmountVO> {

	public void saveAmountsBatch(String tableName, List<T> amounts);

}
