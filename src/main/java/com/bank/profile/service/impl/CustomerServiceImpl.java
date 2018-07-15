package com.bank.profile.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.bank.profile.model.DaoFactory;
import com.bank.profile.model.DataSourceTypes;
import com.bank.profile.model.dao.impl.MongoCustomerDao;
import com.bank.profile.model.vo.mongo.MongoCustomerVO;
import com.bank.profile.service.CustomerService;

public class CustomerServiceImpl implements CustomerService<MongoCustomerVO> {

	private final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private MongoCustomerDao customerDao;

	public CustomerServiceImpl() throws IOException {
		customerDao = new MongoCustomerDao(DaoFactory.getDaoFactory(DataSourceTypes.MONGODB));
	}

	@Override
	public void saveCustomer(ConsumerRecord<String, String> customerRecord) {
		MongoCustomerVO customerVO = new MongoCustomerVO();
		customerDao.saveCustomer(customerVO);
	}

	@Override
	public void saveCustomerBatch(List<ConsumerRecord<String, String>> customerRecords) throws Exception {
		List<MongoCustomerVO> customerList = new ArrayList<>();
		// a,1,홍길동,2018-06-30 13:00:00
		for (ConsumerRecord<String, String> record : customerRecords) {
			MongoCustomerVO vo = new MongoCustomerVO();
			String[] data = record.value().split(",");
			vo.setId(data[1]);
			vo.setCustomerNumber(data[1]);
			vo.setName(data[2]);
			vo.setJoinDt(new SimpleDateFormat(DATE_FORMAT).parse(data[3]));
			customerList.add(vo);
		}
		customerDao.saveCustomerBatch(customerList);
	}

	@Override
	public MongoCustomerVO findByCustomerId(String customerNumber) {
		return customerDao.findByCustomerId(customerNumber);
	}

}
