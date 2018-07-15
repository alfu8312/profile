package com.bank.profile.util;

public class KeyUtils {

	public static String getPartitionKey(String key) {
		String[] keys = ((String) key).split(" ");
		// LogTypes.CUSTOMER round robin
		if (LogTypes.CUSTOMER.equals(keys[0])) {
			return keys[0];
		} else {
			return keys[1];
		}
	}
}
