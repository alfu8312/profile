package com.bank.profile.producer;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.bank.profile.util.LogTypes;
import com.bank.profile.util.PropUtils;

class ProducerThread implements Runnable {

	protected ConcurrentLinkedQueue<String> queue = null;

	private final long MIN_IDLE_TIME_MILLIS = 5000;

	private final KafkaProducer<String, String> producer;
	private final String topic;
	private boolean isRunnable;
	private boolean isStart = false;
	private boolean isEmptyQueue = false;

	private long startIdleTimeMillis = 0;

	public ProducerThread(ConcurrentLinkedQueue<String> queue, String topic, boolean isRunnable) throws IOException {
		this.queue = queue;
		this.producer = new KafkaProducer<>(PropUtils.getProducerConfig());
		this.topic = topic;
		this.isRunnable = isRunnable;
	}

	public String getKey(String data) {
		String[] keys = data.split(",");
		return LogTypes.CUSTOMER.equals(keys[0]) ? keys[0] : keys[0] + " " + keys[2];
	}

	@Override
	public void run() {
		try {
			while (isRunnable) {
				String logData;
				if (null != (logData = queue.poll())) {
					initRunStatus();
					ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, getKey(logData),
							logData);
					producer.send(record, (metadata, exception) -> {
						if (exception != null) {
							exception.printStackTrace();
							queue.offer(logData); // 유실 방지
						}
						// System.out.println("key : " + record.key() + ", data: " + record.value());
					});
				} else {
					checkMinIdleTime();
				}
			}
		} finally {
			producer.close();
		}
	}

	private void checkMinIdleTime() {
		if (isStart && !isEmptyQueue) {
			isEmptyQueue = true;
			startIdleTimeMillis = System.currentTimeMillis();
		} else if (isStart && isEmptyQueue) {
			if (System.currentTimeMillis() - startIdleTimeMillis > MIN_IDLE_TIME_MILLIS) {
				isRunnable = false;
			}
		}
	}

	private void initRunStatus() {
		if (!isStart)
			isStart = true;
		if (isEmptyQueue)
			isEmptyQueue = false;
	}

}