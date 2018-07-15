package com.bank.profile.model;

import org.bson.codecs.configuration.CodecRegistry;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class MongoDBClient {
	private static String url;

	private MongoDBClient() {
	}

	private static class LazyHolder {
		private static final MongoClient instance = MongoClients.create(url);
	}

	public static MongoClient getInstance(String url, CodecRegistry pojoCodecRegistry) {
		if (MongoDBClient.url == null) {
			MongoDBClient.url = url;
		}
		return LazyHolder.instance;
	}
}
