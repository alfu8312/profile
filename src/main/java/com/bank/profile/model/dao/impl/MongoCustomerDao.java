package com.bank.profile.model.dao.impl;

import java.util.List;

import com.bank.profile.model.DaoFactory;
import com.bank.profile.model.MongoDaoFactory;
import com.bank.profile.model.dao.CustomerDao;
import com.bank.profile.model.vo.mongo.MongoCustomerVO;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;

public class MongoCustomerDao implements CustomerDao<MongoCustomerVO> {

	private MongoDaoFactory daoFactory;
	private final String COLLECTION_NAME = "test";

	private MongoCollection<MongoCustomerVO> getCollection() {
		return daoFactory.getDatabase().getCollection(COLLECTION_NAME, MongoCustomerVO.class);
	}

	public MongoCustomerDao(DaoFactory daoFactory) {
		this.daoFactory = (MongoDaoFactory) daoFactory;
	}

	@Override
	public void saveCustomer(MongoCustomerVO customerVO) {
		getCollection().insertOne(customerVO);
	}

	@Override
	public void saveCustomerBatch(List<MongoCustomerVO> customerList) {
		getCollection().insertMany(customerList);
	}

	@Override
	public MongoCustomerVO findByCustomerId(int customerNumber) {
		BasicDBObject query = new BasicDBObject();
		query.put("customerNumber", customerNumber);
		return getCollection().find(query).first();
	}
	
	public void deleteAllCustomer() {
		getCollection().deleteMany(new BasicDBObject());
	}
}
