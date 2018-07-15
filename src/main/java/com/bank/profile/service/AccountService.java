package com.bank.profile.service;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface AccountService<T> {

	public void saveAccountInfo(List<ConsumerRecord<String, String>> accountRecords) throws Exception;
}
