package com.bank.profile.consumer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.bank.profile.model.DataSourceTypes;
import com.bank.profile.service.AccountService;
import com.bank.profile.service.CustomerService;
import com.bank.profile.service.impl.AccountServiceImpl;
import com.bank.profile.service.impl.CustomerServiceImpl;
import com.bank.profile.util.LogTypes;
import com.bank.profile.util.PropUtils;

public class Consumer {

	private final KafkaConsumer<String, String> consumer;
	private final String topic;

	private CustomerService customerService;
	private AccountService accountService;

	public Consumer(String topic, DataSourceTypes dataSourceType) throws IOException {
		this.consumer = new KafkaConsumer<>(PropUtils.getConsumerConfig());
		this.topic = topic;
		if (dataSourceType == null) {
			customerService = new CustomerServiceImpl();
			accountService = new AccountServiceImpl();
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
				// TODO multiThread 테스트
				parallelConsume();
			}
		} finally {
			consumer.close();
		}
	}

	private void parallelConsume() {
		boolean[] hasException = { false };
		ConsumerRecords<String, String> records = consumer.poll(200);
		if (!records.isEmpty()) {
			// System.out.println("get records!!!! : " + records.count());
			ForkJoinPool myPool = new ForkJoinPool(2);
			myPool.submit(() -> {
				records.partitions().parallelStream().forEach(partition -> {
					// System.out.println(Thread.currentThread().getName() + " : " +
					// partition.partition());

					Map<String, List<ConsumerRecord<String, String>>> mapped = getMappedRecords(
							records.records(partition));
					List<ConsumerRecord<String, String>> customerList = null;
					List<ConsumerRecord<String, String>> accountList = null;
					try {
						customerList = mapped.get(LogTypes.CUSTOMER);
						if (!customerList.isEmpty()) {
							customerService.saveCustomerBatch(mapped.get(LogTypes.CUSTOMER));
						}
					} catch (Exception e) {
						e.printStackTrace();
						consumer.seek(partition, customerList.get(0).offset());
						hasException[0] = true;
					}
					try {
						accountList = mapped.get(LogTypes.ACCOUNT);
						if (!accountList.isEmpty()) {
							// TODO accountList 를 계좌별로 분리후 병렬 처리 가능..
							accountService.saveAccountInfo(accountList);
						}
					} catch (Exception e) {
						e.printStackTrace();
						consumer.seek(partition, accountList.get(0).offset());
						hasException[0] = true;
					}

					if (!hasException[0]) {
						// System.out.println("commit before");
						consumer.commitSync();
						// System.out.println("commit after");
					}
				});
			});

		}
	}

	private Map<String, List<ConsumerRecord<String, String>>> getMappedRecords(
			List<ConsumerRecord<String, String>> partitionRecords) {
		Map<String, List<ConsumerRecord<String, String>>> mappedRecords = new HashMap<>();
		List<ConsumerRecord<String, String>> customerList = new ArrayList<>();
		List<ConsumerRecord<String, String>> accountList = new ArrayList<>();

		partitionRecords.forEach(record -> {
			if (LogTypes.CUSTOMER.equals(record.key())) {
				customerList.add(record);
			} else {
				// TODO 계좌별로 분리해서 계좌별 thread 호출로 변경
				accountList.add(record);
			}
		});
		mappedRecords.put(LogTypes.CUSTOMER, customerList);
		mappedRecords.put(LogTypes.ACCOUNT, accountList);
		return mappedRecords;
	}
}
