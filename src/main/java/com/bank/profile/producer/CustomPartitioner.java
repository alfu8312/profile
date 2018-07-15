package com.bank.profile.producer;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import com.bank.profile.util.KeyUtils;
import com.bank.profile.util.LogTypes;

public class CustomPartitioner implements Partitioner {

	private AtomicInteger counter = new AtomicInteger(0);

	public void configure(Map<String, ?> configs) {
	}

	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		String partitionKey = KeyUtils.getPartitionKey((String) key);
		// LogTypes.CUSTOMER round robin
		if (LogTypes.CUSTOMER.equals(partitionKey)) {
			int partition = counter.getAndIncrement();
			if (partition == Integer.MAX_VALUE) {
				counter.set(0);
				return 0;
			}
			return partition % cluster.partitionCountForTopic(topic);
		} else {
			return partitionKey.hashCode() % cluster.partitionCountForTopic(topic);
		}
	}

	public void close() {
	}
}
