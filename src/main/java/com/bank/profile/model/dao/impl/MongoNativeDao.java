package com.bank.profile.model.dao.impl;

import org.bson.Document;

import com.bank.profile.model.DaoFactory;
import com.bank.profile.model.MongoDaoFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;

public class MongoNativeDao {

	private MongoDaoFactory daoFactory;

	private String[] collections = new String[] { "account", "customer", "deposit", "transfer", "withdrawal" };

	public MongoNativeDao(DaoFactory daoFactory) {
		this.daoFactory = (MongoDaoFactory) daoFactory;
	}

	private MongoCollection<Document> getCollection(String collectionName) {
		return daoFactory.getDatabase().getCollection(collectionName);
	}

	public void removeAllCollections() {
		for (String collectionName : collections) {
			getCollection(collectionName).deleteMany(new BasicDBObject());
		}
	}

}
