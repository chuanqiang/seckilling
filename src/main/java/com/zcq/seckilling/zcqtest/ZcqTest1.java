package com.zcq.seckilling.zcqtest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ZcqTest1 {

	volatile int result;

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public static void main(String[] args) {
		ZcqTest1 threadSafeCache = new ZcqTest1();

		for (int i = 0; i < 8; i++) {
			new Thread(() -> {
				int x = 0;
				while (threadSafeCache.getResult() < 100) {
					x++;
				}
				System.out.println(x);
			}).start();
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		threadSafeCache.setResult(200);
	}
}
