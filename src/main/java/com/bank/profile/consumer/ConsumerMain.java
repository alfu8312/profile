package com.bank.profile.consumer;

import java.io.IOException;

public class ConsumerMain {

	public static void main(String[] args) {

		String topic = "test2";

		if (args != null) {
			topic = args[0];
		}
		try {
			Consumer consumer = new Consumer(topic);
			consumer.excute();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
