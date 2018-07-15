package com.bank.profile.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.bank.profile.model.DaoFactory;
import com.bank.profile.model.DataSourceTypes;
import com.bank.profile.model.dao.impl.MongoAccountDao;
import com.bank.profile.model.dao.impl.MongoAmountDao;
import com.bank.profile.model.vo.mongo.MongoAccountVO;
import com.bank.profile.model.vo.mongo.MongoAmountVO;
import com.bank.profile.service.AccountService;
import com.bank.profile.util.LogTypes;

public class AccountServiceImpl implements AccountService<MongoAccountVO> {

	private final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private MongoAccountDao accountDao;
	private MongoAmountDao amountDao;

	public AccountServiceImpl() throws IOException {
		accountDao = new MongoAccountDao(DaoFactory.getDaoFactory(DataSourceTypes.MONGODB));
		amountDao = new MongoAmountDao(DaoFactory.getDaoFactory(DataSourceTypes.MONGODB));
	}

	@Override
	public void saveAccountInfo(List<ConsumerRecord<String, String>> accountRecords) throws Exception {

		Map<String, MongoAccountVO> accountMap = new HashMap<>();
		Map<String, List<MongoAmountVO>> amountMap = new HashMap<>();

		List<MongoAmountVO> deposits = new ArrayList<>();
		List<MongoAmountVO> withdrawals = new ArrayList<>();
		List<MongoAmountVO> transfers = new ArrayList<>();
		for (ConsumerRecord<String, String> record : accountRecords) {
			String[] data = record.value().split(",");
			if (accountMap.containsKey(data[2])) {
				MongoAccountVO accountVO = accountMap.get(data[2]);
				setAccountInfo(deposits, withdrawals, transfers, data, accountVO);
			} else {
				MongoAccountVO accountVO = new MongoAccountVO();
				setAccountInfo(deposits, withdrawals, transfers, data, accountVO);
				accountMap.put(data[2], accountVO);
			}
		}

//		for (MongoAccountVO account : accountMap.values()) {
//			accountDao.saveAccount(account);
//		}

		accountMap.values().parallelStream().forEach(accountVO -> {
			accountDao.saveAccount(accountVO);
		});
		//
		amountMap.put(MongoAmountDao.COLLECTION_DEPOSIT, deposits);
		amountMap.put(MongoAmountDao.COLLECTION_TRANSFER, transfers);
		amountMap.put(MongoAmountDao.COLLECTION_WITHDRAWAL, withdrawals);
		//
		amountMap.entrySet().parallelStream().forEach(amountEntry -> {
			amountDao.saveAmountsBatch(amountEntry.getKey(), amountEntry.getValue());
		});
	}

	private void setAccountInfo(List<MongoAmountVO> deposits, List<MongoAmountVO> withdrawals,
			List<MongoAmountVO> transfers, String[] data, MongoAccountVO accountVO) throws ParseException {
		accountVO.setId(data[2]);
		if (LogTypes.ACCOUNT.equals(data[0])) {
			accountVO.setCustomerNumber(data[1]);
			accountVO.setAccountNumber(data[2]);
			accountVO.setCreateDt(new SimpleDateFormat(DATE_FORMAT).parse(data[3]));
		} else if (LogTypes.DEPOSIT.equals(data[0])) {
			Integer deposit = Integer.parseInt(data[3]);
			accountVO.setBalance(accountVO.getBalance() + deposit);
			accountVO.setMaxDeposit(getMax(accountVO.getMaxDeposit(), deposit));
			deposits.add(new MongoAmountVO(data[2], deposit, new SimpleDateFormat(DATE_FORMAT).parse(data[4])));
		} else if (LogTypes.WITHDRAWAL.equals(data[0])) {
			Integer withdraw = Integer.parseInt(data[3]);
			accountVO.setBalance(accountVO.getBalance() - withdraw);
			accountVO.setMaxWithdraw(getMax(accountVO.getMaxWithdraw(), withdraw));
			withdrawals.add(new MongoAmountVO(data[2], withdraw, new SimpleDateFormat(DATE_FORMAT).parse(data[4])));
		} else if (LogTypes.TRANSFER.equals(data[0])) {
			Integer transfer = Integer.parseInt(data[6]);
			accountVO.setBalance(accountVO.getBalance() - transfer);
			accountVO.setMaxTransfer(getMax(accountVO.getMaxTransfer(), transfer));
			transfers.add(new MongoAmountVO(data[2], transfer, new SimpleDateFormat(DATE_FORMAT).parse(data[7])));
		}
	}

	private int getMax(Integer oldNum, Integer newNum) {
		return oldNum < newNum ? newNum : oldNum;
	}

}
