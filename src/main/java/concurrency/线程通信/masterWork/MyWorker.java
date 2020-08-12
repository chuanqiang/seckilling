package concurrency.线程通信.masterWork;

public class MyWorker extends Worker {
	public static Object handle(Task input) {
		Object output = null;
		try {
			// 表示task任务的耗时，可能是数据的加工或者数据库的操作
			Thread.sleep(500);
			output = input.getPrice();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return output;
	}
}
