package com.bank.profile.model;

import com.bank.profile.model.dao.AccountDao;
import com.bank.profile.model.dao.CustomerDao;

@SuppressWarnings("rawtypes")
public class MysqlDaoFactory extends DaoFactory {

	@Override
	public AccountDao getAccountDao() {
		// TODO create MysqlAccountDao
		return null;
	}

	@Override
	public CustomerDao getCustomerDao() {
		// TODO create MysqlCustomerDao
		return null;
	}

}
