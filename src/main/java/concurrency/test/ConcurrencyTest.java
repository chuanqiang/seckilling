package concurrency.test;

import concurrency.annoations.NotThreadSafe;
import jdk.nashorn.internal.objects.annotations.Getter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;

@NotThreadSafe
public class ConcurrencyTest {
	// 请求总数
	/*public static int clientTotal = 5000;
	// 并发的线程数
	public static int threadTotal = 200;

//	public static Integer count = 0;
	public static AtomicInteger count = new AtomicInteger(0);*/

	/*public static void main(String[] args) throws Exception {
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
		System.out.println("count:" + count.get());
	}*/

	/*private static void add() {
		count.incrementAndGet();
//		count ++;
	}*/

	/*private static AtomicReference<Integer> count1 = new AtomicReference<>(0);

	public static void main(String[] args) {
		count.compareAndSet(0,2);
		count.compareAndSet(0,3);
		count.compareAndSet(2,4);
		System.out.println("count=" + count.get());
	}*/

	private static AtomicIntegerFieldUpdater<ConcurrencyTest> updater = AtomicIntegerFieldUpdater.newUpdater(ConcurrencyTest.class, "count");
	private volatile int count = 100;

	public int getCount() {
		return count;
	}

	public static void main(String[] args) {
		ConcurrencyTest test = new ConcurrencyTest();
		if(updater.compareAndSet(test, 100, 120)){
			System.out.println(test.count);
		}
	}
}
