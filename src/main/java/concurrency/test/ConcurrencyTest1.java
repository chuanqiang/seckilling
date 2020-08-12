package concurrency.test;

import concurrency.annoations.NotThreadSafe;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;

@NotThreadSafe
public class ConcurrencyTest1 {
	// 请求总数
	public static int clientTotal = 5000;
	// 并发的线程数
	public static int threadTotal = 200;

//	public static LongAdder count = new LongAdder();
	public static AtomicLong count = new AtomicLong();

	public static void main(String[] args) throws Exception {
		ExecutorService executorService = Executors.newCachedThreadPool();
		final Semaphore semaphore = new Semaphore(threadTotal);
		final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
		for (int i = 0; i < clientTotal; i++) {
			executorService.execute(() -> {
				try {
					semaphore.acquire();
					add();
					semaphore.release();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				countDownLatch.countDown();
			});
		}
		countDownLatch.await();
		executorService.shutdown();
		System.out.println("count:" + count);
	}

	private static void add() {
//		count.increment();
		count.incrementAndGet();
	}
}
