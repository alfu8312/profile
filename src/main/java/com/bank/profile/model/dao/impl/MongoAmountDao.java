package com.bank.profile.model.dao.impl;

import java.util.List;

import com.bank.profile.model.DaoFactory;
import com.bank.profile.model.MongoDaoFactory;
import com.bank.profile.model.dao.AmountDao;
import com.bank.profile.model.vo.mongo.MongoAmountVO;
import com.mongodb.client.MongoCollection;

public class MongoAmountDao implements AmountDao<MongoAmountVO> {

	public static final String COLLECTION_DEPOSIT = "deposit";
	public static final String COLLECTION_WITHDRAWAL = "withdrawal";
	public static final String COLLECTION_TRANSFER = "transfer";

	private MongoDaoFactory daoFactory;

	public MongoAmountDao(DaoFactory daoFactory) {
		this.daoFactory = (MongoDaoFactory) daoFactory;
	}

	private MongoCollection<MongoAmountVO> getCollection(String collectionName) {
		return daoFactory.getDatabase().getCollection(collectionName, MongoAmountVO.class);
	}

	@Override
	public void saveAmountsBatch(String collectionName, List<MongoAmountVO> amounts) {
		if (!amounts.isEmpty()) {
			System.out.println(
					"saveAmounts : " + amounts.size() + Thread.currentThread().getName() + " : " + collectionName);
			getCollection(collectionName).insertMany(amounts);
		}
	}

}