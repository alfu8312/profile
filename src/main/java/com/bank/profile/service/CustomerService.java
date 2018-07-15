package com.bank.profile.service;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface CustomerService<T> {

	public void saveCustomer(ConsumerRecord<String, String> customerRecord);

	public void saveCustomerBatch(List<ConsumerRecord<String, String>> customerRecords) throws Exception;

	public T findByCustomerId(String customerNumber);
}
