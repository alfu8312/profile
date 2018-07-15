package com.bank.profile.model.dao.impl;

import org.bson.Document;

import com.bank.profile.model.DaoFactory;
import com.bank.profile.model.MongoDaoFactory;
import com.bank.profile.model.dao.AccountDao;
import com.bank.profile.model.vo.mongo.MongoAccountVO;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;

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
		System.out.println("account dao : " + Thread.currentThread().getName());

		BasicDBObject updateDoc = new BasicDBObject("$max",
				new BasicDBObject().append("accountNumber", account.getId()).append("balance", account.getBalance())
						.append("maxDeposit", account.getMaxDeposit()).append("maxWithdraw", account.getMaxWithdraw())
						.append("maxTransfer", account.getMaxTransfer()));

		BasicDBObject setDoc = new BasicDBObject();
		if (account.getCustomerNumber() != null) {
			setDoc.append("customerNumber", account.getCustomerNumber());
		}
		if (account.getCreateDt() != null) {
			setDoc.append("createDt", account.getCreateDt());
		}

		if (!setDoc.isEmpty()) {
			updateDoc.append("$set", setDoc);
		}

		getCollection().updateOne(new Document("_id", account.getId()), updateDoc, new UpdateOptions().upsert(true));
	}

	@Override
	public MongoAccountVO findByAccountId(int accountNumber) {
		// TODO Auto-generated method stub
		return null;
	}


}
