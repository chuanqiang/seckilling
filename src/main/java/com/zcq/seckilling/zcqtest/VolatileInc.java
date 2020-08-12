package com.zcq.seckilling.zcqtest;

import java.util.concurrent.atomic.AtomicInteger;

public class VolatileInc implements Runnable{
	//使用 volatile 修饰基本数据内存不能保证原子性
	//private static volatile int count = 0 ;
	private static AtomicInteger count = new AtomicInteger() ;
	@Override
	public void run() {
		for (int i=0;i<10000 ;i++){
			//count ++ ;
			count.incrementAndGet() ;
		}
	}
	public static void main(String[] args) throws InterruptedException {
		VolatileInc volatileInc = new VolatileInc() ;
		Thread t1 = new Thread(volatileInc,"t1") ;
		Thread t2 = new Thread(volatileInc,"t2") ;
		t1.start();
		// join() 方法使线程之间的并行变为串行执行
		t1.join();
		t2.start();
		t2.join();
		for (int i=0;i<10000 ;i++){
			//count ++ ;
			count.incrementAndGet();
		}
		System.out.println("最终Count="+count);
	}
}
