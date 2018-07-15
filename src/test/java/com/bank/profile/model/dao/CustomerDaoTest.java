package com.bank.profile.model.dao;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import org.bson.Document;
import org.junit.Before;
import org.junit.Test;

import com.bank.profile.model.DaoFactory;
import com.bank.profile.model.DataSourceTypes;
import com.bank.profile.model.MongoDaoFactory;
import com.bank.profile.model.dao.impl.MongoCustomerDao;
import com.bank.profile.model.vo.mongo.MongoCustomerVO;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.UpdateOptions;

public class CustomerDaoTest {
	private CustomerDao customerDao;

	@Before
	public void init() throws IOException {
		DaoFactory daoFactory = DaoFactory.getDaoFactory(DataSourceTypes.MONGODB);
		customerDao = new MongoCustomerDao(daoFactory);
	}

	@Test
	public void id() throws IOException {
		MongoDaoFactory daoFactory = (MongoDaoFactory) DaoFactory.getDaoFactory(DataSourceTypes.MONGODB);
		MongoCollection<Document> db = daoFactory.getDatabase().getCollection("customer");

		UpdateOneModel<Document> model = new UpdateOneModel<Document>(
				new Document("_id", "3"), // find part
				new Document("$set", new BasicDBObject().append("name", "김도적2")), // update part
				new UpdateOptions().upsert(true) // options like upsert
		);
		db.bulkWrite(Arrays.asList(model));
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
