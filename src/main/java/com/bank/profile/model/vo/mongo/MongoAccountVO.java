package com.bank.profile.model.vo.mongo;

import org.bson.types.ObjectId;

import com.bank.profile.model.vo.AccountVO;

public class MongoAccountVO extends AccountVO {

	private ObjectId id;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}
}
