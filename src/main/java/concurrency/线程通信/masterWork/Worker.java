package concurrency.线程通信.masterWork;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Worker implements Runnable {
	private ConcurrentLinkedQueue<Task> workQueue;
	private ConcurrentHashMap<String, Object> resultMap;

	public void setWorkQueue(ConcurrentLinkedQueue<Task> workQueue) {
		this.workQueue = workQueue;
	}

	public void setResultMap(ConcurrentHashMap<String, Object> resultMap) {
		this.resultMap = resultMap;
	}

	@Override
	public void run() {
		while (true) {
			// 取出元素并且删除
			Task input = this.workQueue.poll();
			if (input == null) {
				break;
			}
			Object output = MyWorker.handle(input);
			this.resultMap.put(Integer.toString(input.getId()), output);
		}
	}

	public static Object handle(Task input) {
		return null;
	}

}
