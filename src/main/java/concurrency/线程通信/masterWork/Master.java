package concurrency.线程通信.masterWork;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Master {
	// 1. 应该有一个盛装任务的集合
	private ConcurrentLinkedQueue<Task> workQueue = new ConcurrentLinkedQueue<Task>();
	// 2. 使用hashMap盛装所有的worker对象
	private HashMap<String, Thread> workers = new HashMap<String, Thread>();
	// 3. 使用ConcurrentHashMap 容器盛装所有的work并发执行的结果集、
	private ConcurrentHashMap<String, Object> resultMap = new ConcurrentHashMap<String, Object>();

	// 4. 构造器
	public Master(Worker worker, int workerCount) {
		// 每一个worker对象需要有master的引用：workQueue用于worker的领取；resultMap用于任务的提交
		worker.setWorkQueue(this.workQueue);
		worker.setResultMap(this.resultMap);

		for (int i = 0; i < workerCount; i++) {
			workers.put("子节点" + Integer.toString(i), new Thread(worker));
		}
	}

	// 5. 提交方法
	public void submit(Task task) {
		this.workQueue.add(task);
	}

	// 6. 执行方法
	public void execute() {
		for (Map.Entry<String, Thread> me : workers.entrySet()) {
			me.getValue().start();
		}
	}

	// 判断线程是否执行完毕
	public boolean isComplete() {
		for (Map.Entry<String, Thread> me : workers.entrySet()) {
			if (me.getValue().getState() != Thread.State.TERMINATED) {
				return false;
			}
		}
		return true;
	}

	// 返回结果集
	public int getResult() {
		int result = 0;
		for (Map.Entry<String, Object> me : resultMap.entrySet()) {
			result += (Double) me.getValue();
		}
		return result;
	}
}
