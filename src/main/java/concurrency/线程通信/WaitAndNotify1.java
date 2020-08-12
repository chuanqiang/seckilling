package concurrency.线程通信;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Description: 相比较使用 wait / notify，此方法能实时通信，而不用等线程1 执行完再通信。
 * @Author: zcq
 * @Date: 2018-12-01 23:12
 */
public class WaitAndNotify1 {
	private volatile static List list = new ArrayList();

	private void add() {
		list.add("zcq");
	}

	private int size() {
		return list.size();
	}

	public static void main(String[] args) {
		WaitAndNotify1 wan = new WaitAndNotify1();
		final CountDownLatch count = new CountDownLatch(1);

		Thread t1 = new Thread(() -> {
			try {
				for (int i = 0; i < 10; i++) {
					wan.add();
					System.out.println(Thread.currentThread().getName() + "添加了一个元素...");
					Thread.sleep(500);
					if (wan.size() == 5) {
						count.countDown();
						System.out.println("当前线程已发出通知");
					}

				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, "t1");

		Thread t2 = new Thread(() -> {
			if (wan.size() != 5) {
				try {
					count.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + "收到通知线程停止");
				throw new RuntimeException();
			}
		});
		t2.start();
		t1.start();
	}
}
