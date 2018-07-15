package com.bank.profile.model.vo.mongo;

import java.util.Date;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import com.bank.profile.model.vo.AmountVO;

public class MongoAmountVO extends AmountVO {

	public MongoAmountVO(String accountNumber, Integer amount, Date createDt) {
		super(accountNumber, amount, createDt);
	}

	@BsonId
	private ObjectId id;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

}
