package com.bank.profile.producer;

import com.bank.profile.GeneratorSample;

public class ProducerMain {

	public static void main(String[] args) {
		String topic = "test2";

		if (args != null && args.length > 0) {
			topic = args[0];
		}
		System.out.printf("topic: %s\n", topic);

		// TODO generate sample data
		String filePath = GeneratorSample.generateFile();
		Producer producer = null;
		try {
			producer = new Producer(topic, filePath);
			producer.excute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			producer.shutdown();
		}

	}

}
