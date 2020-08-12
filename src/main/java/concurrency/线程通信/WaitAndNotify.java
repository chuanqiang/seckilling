package concurrency.线程通信;

import java.util.ArrayList;
import java.util.List;

public class WaitAndNotify {
	private volatile static List list = new ArrayList();

	private void add() {
		list.add("zcq");
	}

	private int size(){
		return list.size();
	}

	public static void main(String[] args) {
		WaitAndNotify wan = new WaitAndNotify();

		final Object lock = new Object();

		Thread t1 = new Thread(() -> {
			try {
				synchronized (lock) {
					for (int i = 0; i < 10; i++) {
						wan.add();
						System.out.println(Thread.currentThread().getName() + "添加了一个元素...");
						Thread.sleep(500);
						if (wan.size() == 5) {
							System.out.println("当前线程已发出通知");
							lock.notify();
						}

					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		},"t1");

		Thread t2 = new Thread(() -> {
			synchronized (lock) {
				if (wan.size() != 5) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName() + "收到通知线程停止");
					throw new RuntimeException();
				}
			}
		});
		t2.start();
		t1.start();
	}
}
