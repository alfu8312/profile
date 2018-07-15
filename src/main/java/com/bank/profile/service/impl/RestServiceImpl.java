package com.bank.profile.service.impl;

import java.io.IOException;
import java.util.List;

import com.bank.profile.model.DaoFactory;
import com.bank.profile.model.DataSourceTypes;
import com.bank.profile.model.dao.impl.MongoAccountDao;
import com.bank.profile.model.dao.impl.MongoAmountDao;
import com.bank.profile.model.dao.impl.MongoCustomerDao;
import com.bank.profile.model.dao.impl.MongoNativeDao;
import com.bank.profile.model.vo.AccountProfileVO;
import com.bank.profile.model.vo.CustomerProfileVO;
import com.bank.profile.model.vo.mongo.MongoAccountVO;
import com.bank.profile.model.vo.mongo.MongoCustomerVO;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RestServiceImpl {

	private DaoFactory daoFactory;
	private MongoNativeDao nativeDao;
	private MongoCustomerDao customerDao;
	private MongoAccountDao accountDao;
	private MongoAmountDao amountDao;

	private Gson gson;

	public RestServiceImpl() {
		try {
			daoFactory = DaoFactory.getDaoFactory(DataSourceTypes.MONGODB);
			nativeDao = new MongoNativeDao(daoFactory);
			customerDao = new MongoCustomerDao(daoFactory);
			accountDao = new MongoAccountDao(daoFactory);
			amountDao = new MongoAmountDao(daoFactory);

			gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
					.setPrettyPrinting().create();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String removeAllCollections() {
		try {
			nativeDao.removeAllCollections();
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
		return "ok";
	}

	public String selectCustomerProfile(String customerNumber) {

		CustomerProfileVO customerProfileVO = new CustomerProfileVO();
		try {

			MongoCustomerVO customerVO = customerDao.findByCustomerId(customerNumber);
			System.out.println(customerVO);
			customerProfileVO.setCustomerNumber(customerVO.getCustomerNumber());
			customerProfileVO.setName(customerVO.getName());
			customerProfileVO.setJoinDt(customerVO.getJoinDt());
			List<MongoAccountVO> accountList = accountDao.findByCustomerId(customerNumber);
			int largestDepositAmount = 0;
			int largestTransferAmount = 0;
			int largestWithdrawAmount = 0;

			for (MongoAccountVO vo : accountList) {
				largestDepositAmount = getMax(largestDepositAmount, vo.getMaxDeposit());
				largestTransferAmount = getMax(largestTransferAmount, vo.getMaxTransfer());
				largestWithdrawAmount = getMax(largestWithdrawAmount, vo.getMaxWithdraw());
			}
			customerProfileVO.setLargestDepositAmount(largestDepositAmount);
			customerProfileVO.setLargestTransferAmount(largestTransferAmount);
			customerProfileVO.setLargestWithdrawAmount(largestWithdrawAmount);
			System.out.println(customerProfileVO);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return gson.toJson(customerProfileVO);

	}

	public String selectAccountProfile(String customerNumber, String accountNumber) {
		AccountProfileVO accountProfileVO = new AccountProfileVO();
		try {
			MongoAccountVO account = accountDao.findByAccountId(accountNumber);

			accountProfileVO.setAccountNumber(account.getAccountNumber());
			accountProfileVO.setCustomerNumber(account.getCustomerNumber());
			accountProfileVO.setBalance(account.getBalance());
			accountProfileVO.setCreateDt(account.getCreateDt());

			accountProfileVO.setDeposits(amountDao.findByAccountId(MongoAmountDao.COLLECTION_DEPOSIT, accountNumber));
			accountProfileVO.setTransfers(amountDao.findByAccountId(MongoAmountDao.COLLECTION_TRANSFER, accountNumber));
			accountProfileVO
					.setWithdrawals(amountDao.findByAccountId(MongoAmountDao.COLLECTION_WITHDRAWAL, accountNumber));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(accountProfileVO);
	}

	private int getMax(int oldNum, int newNum) {
		return oldNum < newNum ? newNum : oldNum;
	}

}
