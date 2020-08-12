package concurrency.threadPool;

import java.util.concurrent.*;

public class UseExecutors {
	public static void main(String[] args) {
		Task task = new Task();
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		ScheduledFuture<?> scheduleTask = scheduler.scheduleWithFixedDelay(task, 1, 2, TimeUnit.SECONDS);
	}
}

class Task extends Thread {
	@Override
	public void run() {
		System.out.println("run");
	}
}
