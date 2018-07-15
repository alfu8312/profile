package com.bank.profile.model.dao;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.bank.profile.model.DaoFactory;
import com.bank.profile.model.DataSourceTypes;
import com.bank.profile.model.dao.impl.MongoAccountDao;
import com.bank.profile.model.dao.impl.MongoCustomerDao;
import com.bank.profile.model.dao.impl.MongoNativeDao;
import com.bank.profile.model.vo.mongo.MongoAccountVO;
import com.bank.profile.model.vo.mongo.MongoCustomerVO;
import com.mongodb.BasicDBObject;

public class DaoTest {
	private CustomerDao customerDao;
	private MongoAccountDao accountDao;
	private MongoNativeDao nativeDao;

	@Before
	public void init() throws IOException {
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DataSourceTypes.MONGODB);
		customerDao = new MongoCustomerDao(daoFactory);
		nativeDao = new MongoNativeDao(daoFactory);
		accountDao = new MongoAccountDao(daoFactory);
	}

	@Test
	public void find_account_List() {
		String customerNumber = "1";
		List<MongoAccountVO> list = accountDao.findByCustomerId(customerNumber);
		System.out.println(list);
	}

	@Test
	public void remove_all_collections() {
		String customerNumber = "1";
		MongoCustomerVO customerVO = new MongoCustomerVO();
		customerVO.setCustomerNumber(customerNumber);
		customerVO.setName("alfu");
		customerVO.setJoinDt(new Date());
		customerDao.saveCustomer(customerVO);
		nativeDao.removeAllCollections();
		MongoCustomerVO selectedCustomerVO = (MongoCustomerVO) customerDao.findByCustomerId(customerNumber);
		assertTrue(selectedCustomerVO == null);
	}

	@Test
	public void update_max_account() {
		// private int balance = 0;
		// private int maxDeposit = 0;
		// private int maxWithdraw = 0;
		// private int maxTransfer = 0;
		MongoAccountVO account = new MongoAccountVO();
		account.setId("1_1");
		account.setBalance(1);
		account.setMaxDeposit(2);
		account.setCustomerNumber("1");
		account.setCreateDt(new Date());
		BasicDBObject updateDoc = new BasicDBObject("$max",
				new BasicDBObject().append("accountNumber", account.getId()).append("balance", account.getBalance())
						.append("maxDeposit", account.getMaxDeposit()).append("maxWithdraw", account.getMaxWithdraw())
						.append("maxTransfer", account.getMaxTransfer()));

		BasicDBObject pushDoc = new BasicDBObject();
		System.out.println(pushDoc.isEmpty());
		if (account.getCustomerNumber() != null) {
			pushDoc.append("customerNumber", account.getCustomerNumber());
		}
		if (account.getCreateDt() != null) {
			pushDoc.append("createDt", account.getCreateDt().toString());
		}

		if (!pushDoc.isEmpty()) {
			updateDoc.append("$set", pushDoc);
		}

		System.out.println(pushDoc.isEmpty());

		System.out.println(updateDoc.toJson());
	}

	@Test
	public void insert_customer() {
		String customerNumber = "1";
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
		String customerNumber = "1";
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
