package com.bank.profile.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.bank.profile.producer.CustomPartitioner;

public class PropUtils {

	public enum PropType {
		kafka, db
	}

	private static final String KAFKA_SERVER_PROP = "server.properties";
	private static final String PRODUCER_PROP = "producer.properties";
	private static final String CONSUMER_PROP = "consumer.properties";

	private static Properties loadProperties(String path) throws IOException {
		Properties props = new Properties();
		InputStream is = null;
		try {
			is = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
			props.load(is);
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return props;
	}

	public static Properties getProps(PropType propType, String propName) throws IOException {
		return loadProperties(propType + File.separator + propName);
	}

	public static Properties getProducerConfig() throws IOException {
		Properties props = loadProperties(PropType.kafka + File.separator + KAFKA_SERVER_PROP);
		props.putAll(loadProperties(PropType.kafka + File.separator + PRODUCER_PROP));
		props.put("partitioner.class", CustomPartitioner.class.getName());
		return props;
	}

	public static Properties getConsumerConfig() throws IOException {
		Properties props = loadProperties(PropType.kafka + File.separator + KAFKA_SERVER_PROP);
		props.putAll(loadProperties(PropType.kafka + File.separator + CONSUMER_PROP));
		return props;
	}

}
