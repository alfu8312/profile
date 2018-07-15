package com.bank.profile.producer;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

import org.junit.Test;

public class ProducerTest {

	private String topic = "test1";
	private String filePath = "logs/sample";

	@Test
	public void test() throws ParseException {
		String date = "2018-06-30 13:00:00";
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date));
	}
	
	@Test
	public void excute_sleep() throws Exception {
		Producer producer = new Producer(topic, filePath);
		producer.excute();
		Thread.sleep(10000);
		System.out.println("shutdown");
		producer.shutdown();
		assertTrue(true);
	}

	@Test
	public void excute_add() throws Exception {
		Producer producer = new Producer(topic, filePath);
		producer.excute();
		Thread.sleep(1000);
		producer.addQueue("a,5,추가사용자,2018-06-30 13:00:00");
		producer.shutdown();
		assertTrue(true);
	}

	@Test
	public void excute_add_idleTimeInit() throws Exception {
		Producer producer = new Producer(topic, filePath);
		producer.excute();
		Thread.sleep(1000);
		producer.addQueue("a,5,추가사용자,2018-06-30 13:00:00");
		System.out.println("min idle time init");
		Thread.sleep(10000);
		assertTrue(true);
	}

	@Test
	public void paraller_thread() throws InterruptedException, ExecutionException {
		IntStream.range(0, 10).parallel().forEach(index -> {
			System.out.println(
					"Starting " + Thread.currentThread().getName() + ",    index=" + index + ", " + new Date());
		});
	}

	@Test
	public void paraller_thread_setpool() throws InterruptedException, ExecutionException {
		ForkJoinPool myPool = new ForkJoinPool(5);
		myPool.submit(() -> {
			IntStream.range(0, 10).parallel().forEach(index -> {
				System.out.println(
						"Starting " + Thread.currentThread().getName() + ",    index=" + index + ", " + new Date());
				// try {
				// Thread.sleep(5000);
				// } catch (InterruptedException e) {
				// }
			});
		}).get();
	}

}
