package com.bank.profile.model.dao;

import java.util.List;

import com.bank.profile.model.vo.CustomerVO;

public interface CustomerDao<T extends CustomerVO> {

	public void saveCustomer(T customerVO);

	public void saveCustomerBatch(List<T> customerList);

	public T findByCustomerId(String customerNumber);
	
	public void deleteAllCustomer();

}
