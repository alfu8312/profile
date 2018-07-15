package com.bank.profile.model.vo.mongo;

import org.bson.types.ObjectId;

import com.bank.profile.model.vo.CustomerVO;

public final class MongoCustomerVO extends CustomerVO {

	private ObjectId id;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

}
