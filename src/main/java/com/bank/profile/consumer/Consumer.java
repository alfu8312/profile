package com.bank.profile.consumer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import com.bank.profile.util.PropUtils;

public class Consumer {

	private final KafkaConsumer<String, String> consumer;
	private final String topic;

	public Consumer(String topic) throws IOException {
		this.consumer = new KafkaConsumer<>(PropUtils.getConsumerConfig());
		this.topic = topic;
	}

	public void excute() {
		try {
			consumer.subscribe(Arrays.asList(topic));
			System.out.println("running consumer");
			// type 2 - 파티션 단위로 쓰레드 생성해서 처리
			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(200);
				for (TopicPartition partition : records.partitions()) {
					System.out.println(Thread.currentThread().getName() + " : " + partition.partition());
					List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);
					System.out.println("partitionRecords cnt : " + partitionRecords.size());
					// TODO partitionRecords 단위로 Thread 생성해서 db update, 실패 record 골라서 리턴
					if (saveData(partitionRecords)) {
						long lastOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
						consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastOffset + 1)));
					} else {
						consumer.seek(partition, partitionRecords.get(0).offset());
					}
				}
			}
		} finally {
			consumer.close();
		}
	}

	private static boolean saveData(List<ConsumerRecord<String, String>> partitionRecords) {
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
