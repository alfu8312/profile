package com.bank.profile.consumer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.bank.profile.model.DataSourceTypes;
import com.bank.profile.model.dao.impl.MongoCustomerDao;
import com.bank.profile.service.CustomerService;
import com.bank.profile.service.impl.CustomerServiceImpl;
import com.bank.profile.util.PropUtils;

public class Consumer {

	private final KafkaConsumer<String, String> consumer;
	private final String topic;

	private CustomerService customerService;

	public Consumer(String topic, DataSourceTypes dataSourceType) throws IOException {
		this.consumer = new KafkaConsumer<>(PropUtils.getConsumerConfig());
		this.topic = topic;
		if (dataSourceType == null) {
			customerService = new CustomerServiceImpl();
		} else {
			// TODO dataSourceType check new customerService..
		}
	}

	public void excute() {
		try {
			consumer.subscribe(Arrays.asList(topic));
			System.out.println("running consumer");
			// type 2 - 파티션 단위로 쓰레드 생성해서 처리
			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(200);
				ForkJoinPool myPool = new ForkJoinPool(4);
				myPool.submit(() -> {
					records.partitions().parallelStream().forEach(partition -> {
						System.out.println(Thread.currentThread().getName() + " : " + partition.partition());
						try {
							customerService.saveCustomerBatch(records.records(partition));
						} catch (Exception e) {
							consumer.seek(partition, records.records(partition).get(0).offset());
						}
					});
				});
			}
		} finally {
			consumer.close();
		}
	}

	private static boolean printData(List<ConsumerRecord<String, String>> partitionRecords) {
		try {
			for (ConsumerRecord<String, String> record : partitionRecords) {
				System.out.printf("Receive key: %s, Partition: %d, Offset: %d, Value: %s\n", record.key(),
						record.partition(), record.offset(), record.value());
			}
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}

}
