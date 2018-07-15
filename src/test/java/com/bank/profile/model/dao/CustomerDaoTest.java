package com.bank.profile.model.dao;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.bank.profile.model.DaoFactory;
import com.bank.profile.model.DataSourceTypes;
import com.bank.profile.model.dao.impl.MongoCustomerDao;
import com.bank.profile.model.vo.mongo.MongoCustomerVO;

public class CustomerDaoTest {
	private CustomerDao customerDao;

	@Before
	public void init() throws IOException {
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DataSourceTypes.MONGODB);
		customerDao = new MongoCustomerDao(daoFactory);
	}

	@Test
	public void insert_customer() {
		int customerNumber = 1;
		MongoCustomerVO customerVO = new MongoCustomerVO();
		customerVO.setCustomerNumber(customerNumber);
		customerVO.setName("alfu");
		customerVO.setJoinDt(new Date());
		customerDao.saveCustomer(customerVO);
		MongoCustomerVO selectedCustomerVO = (MongoCustomerVO) customerDao.findByCustomerId(customerNumber);
		assertTrue(selectedCustomerVO.getId().equals(customerVO.getId()));
	}

	@Test
	public void delete_all_customer() {
		int customerNumber = 1;
		MongoCustomerVO customerVO = new MongoCustomerVO();
		customerVO.setCustomerNumber(customerNumber);
		customerVO.setName("alfu");
		customerVO.setJoinDt(new Date());
		customerDao.saveCustomer(customerVO);
		customerDao.deleteAllCustomer();
		MongoCustomerVO selectedCustomerVO = (MongoCustomerVO) customerDao.findByCustomerId(customerNumber);
		assertTrue(selectedCustomerVO == null);
	}
}
