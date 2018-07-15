package com.bank.profile.model.dao.impl;

import static com.mongodb.client.model.Sorts.ascending;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.bank.profile.model.DaoFactory;
import com.bank.profile.model.MongoDaoFactory;
import com.bank.profile.model.dao.AmountDao;
import com.bank.profile.model.vo.mongo.MongoAmountVO;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class MongoAmountDao implements AmountDao<MongoAmountVO> {

	public static final String COLLECTION_DEPOSIT = "deposit";
	public static final String COLLECTION_WITHDRAWAL = "withdrawal";
	public static final String COLLECTION_TRANSFER = "transfer";

	private MongoDaoFactory daoFactory;

	public MongoAmountDao(DaoFactory daoFactory) {
		this.daoFactory = (MongoDaoFactory) daoFactory;
	}

	private MongoCollection<Document> getNativeCollection(String collectionName) {
		return daoFactory.getDatabase().getCollection(collectionName);
	}

	private MongoCollection<MongoAmountVO> getCollection(String collectionName) {
		return daoFactory.getDatabase().getCollection(collectionName, MongoAmountVO.class);
	}

	@Override
	public void saveAmountsBatch(String collectionName, List<MongoAmountVO> amounts) {
		if (!amounts.isEmpty()) {
			System.out.println("save amount batch : " + Thread.currentThread().getName());
			getCollection(collectionName).insertMany(amounts);
		}
	}

	public List<MongoAmountVO> findByAccountId(String collectionName, String accountNumber) {
		List<MongoAmountVO> amountList = new ArrayList<>();
		FindIterable<Document> result = getNativeCollection(collectionName)
				.find(new BasicDBObject("accountNumber", accountNumber)).sort(ascending("createDt"));

		for (Document doc : result) {
			MongoAmountVO vo = new MongoAmountVO(doc.getString("accountNumber"), doc.getInteger("amount", 0), null);
			amountList.add(vo);
		}
		return amountList;
	}

}
