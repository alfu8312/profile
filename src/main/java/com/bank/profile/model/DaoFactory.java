package com.bank.profile.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.bank.profile.model.dao.AccountDao;
import com.bank.profile.model.dao.CustomerDao;
import com.bank.profile.util.PropUtils;
import com.bank.profile.util.PropUtils.PropType;

@SuppressWarnings("rawtypes")
public abstract class DaoFactory {

	private static Map<String, Properties> propMap = new HashMap<>();

	public static DaoFactory getDaoFactory(DataSourceTypes dataSourceType) throws IOException {
		if (dataSourceType == null) {
			throw new NullPointerException("dataSource is null");
		}

		DaoFactory instance = null;
		if (DataSourceTypes.MONGODB.getCode().equals(dataSourceType.getCode())) {
			instance = new MongoDaoFactory(getPropByDataSourceTypes(dataSourceType));
		} else if (DataSourceTypes.MYSQL.getCode().equals(dataSourceType.getCode())) {
			instance = new MysqlDaoFactory();
		}
		return instance;
	}

	private static Properties getPropByDataSourceTypes(DataSourceTypes dataSource) throws IOException {
		if (!propMap.containsKey(dataSource.getPropName())) {
			propMap.put(dataSource.getPropName(), PropUtils.getProps(PropType.db, dataSource.getPropName()));
		}
		return propMap.get(dataSource.getPropName());
	}

	public abstract AccountDao getAccountDao();

	public abstract CustomerDao getCustomerDao();

}
