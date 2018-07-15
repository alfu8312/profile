package com.bank.profile.producer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Stream;

public class Producer {

	private ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();
	private ProducerThread producerThread = null;

	private final String filePath;
	private boolean isRunnable = true;

	public Producer(String topic, String filePath) throws IOException {
		this.filePath = filePath;
		producerThread = new ProducerThread(queue, topic, isRunnable);
	}

	public void excute() {
		Thread t1 = new Thread(producerThread);
		t1.start();
		// Thread t2 = new Thread(producerThread);
		// t2.start();

		Stream<String> lines = null;
		try {
			lines = Files.lines(Paths.get(getClass().getClassLoader().getResource(filePath).toURI()));
			int index[] = new int[] { 0 };
			lines.forEach((line) -> {
				index[0]++;
				queue.offer(line);
			});
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} finally {
			if (lines != null) {
				lines.close();
			}
		}
	}

	public void shutdown() {
		this.isRunnable = false;
	}

	public void addQueue(String data) {
		this.queue.offer(data);
	}
}
