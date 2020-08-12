package concurrency.线程通信.masterWork;

import java.util.Random;

public class Main {
	public static void main(String[] args) {
		System.out.println("我的机器可用的Processor数量：" + Runtime.getRuntime().availableProcessors());
		Master master = new Master(new MyWorker(), Runtime.getRuntime().availableProcessors());
		Random random = new Random();
		// todo 改为线程池
		for (int i = 0; i < 100; i++) {
			Task task = new Task();
			task.setId(i);
			task.setName("任务" + i);
			task.setPrice(random.nextInt(1000));
			master.submit(task);
		}
		master.execute();
		long state = System.currentTimeMillis();
		while (true) {
			if (master.isComplete()) {
				long end = System.currentTimeMillis() - state;
				int result = master.getResult();
				System.out.println("最终结果：" + result + "，最终耗时：" + end);
				break;
			}
		}

	}
}
