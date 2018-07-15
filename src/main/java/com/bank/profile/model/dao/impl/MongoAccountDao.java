package com.bank.profile.model.dao.impl;

import com.bank.profile.model.DaoFactory;
import com.bank.profile.model.MongoDaoFactory;
import com.bank.profile.model.dao.AccountDao;
import com.bank.profile.model.vo.mongo.MongoAccountVO;
import com.mongodb.client.MongoCollection;

public class MongoAccountDao implements AccountDao<MongoAccountVO> {

	private MongoDaoFactory daoFactory;
	private final String COLLECTION_NAME = "account";

	public MongoAccountDao(DaoFactory daoFactory) {
		this.daoFactory = (MongoDaoFactory) daoFactory;
	}

	private MongoCollection<MongoAccountVO> getCollection() {
		return daoFactory.getDatabase().getCollection(COLLECTION_NAME, MongoAccountVO.class);
	}

	@Override
	public void saveAccount(MongoAccountVO account) {
		// TODO Auto-generated method stub

	}

	@Override
	public MongoAccountVO findByAccountId(int accountNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateSummary(MongoAccountVO account) {
		// TODO Auto-generated method stub

	}

}
