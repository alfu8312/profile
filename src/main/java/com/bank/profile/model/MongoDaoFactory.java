package com.bank.profile.model;

import java.util.Properties;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.bank.profile.model.dao.AccountDao;
import com.bank.profile.model.dao.CustomerDao;
import com.bank.profile.model.dao.impl.MongoAccountDao;
import com.bank.profile.model.dao.impl.MongoCustomerDao;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

@SuppressWarnings("rawtypes")
public class MongoDaoFactory extends DaoFactory {

	private Properties props;
	private CodecRegistry pojoCodecRegistry;

	public MongoDaoFactory(Properties props) {
		this.props = props;
		this.pojoCodecRegistry = org.bson.codecs.configuration.CodecRegistries.fromRegistries(
				MongoClientSettings.getDefaultCodecRegistry(), org.bson.codecs.configuration.CodecRegistries
						.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

	}

	public MongoDatabase getDatabase() {
		return getConnection().getDatabase(props.getProperty("database")).withCodecRegistry(pojoCodecRegistry);
	}

	public MongoClient getConnection() {
		return MongoDBClient.getInstance(props.getProperty("url"), this.pojoCodecRegistry);
	}

	public CodecRegistry getPojoCodecRegistry() {
		return pojoCodecRegistry;
	}

	@Override
	public AccountDao getAccountDao() {
		return new MongoAccountDao(this);
	}

	@Override
	public CustomerDao getCustomerDao() {
		return new MongoCustomerDao(this);
	}

}
