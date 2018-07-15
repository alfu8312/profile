package com.bank.profile.model;

public enum DataSourceTypes {
	MONGODB("1", "mongodb.properties"), MYSQL("2", "mysql.properties");

	private String code;
	private String propName;

	DataSourceTypes(String code, String propName) {
		this.code = code;
		this.propName = propName;
	}

	public String getCode() {
		return code;
	}

	public String getPropName() {
		return propName;
	}
}
