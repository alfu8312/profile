package com.bank.profile.model.vo.mongo;

import org.bson.codecs.pojo.annotations.BsonId;

import com.bank.profile.model.vo.AccountVO;

public class MongoAccountVO extends AccountVO {

	@BsonId
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
