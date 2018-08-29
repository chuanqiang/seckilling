package com.zcq.seckilling.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class FairNewTask {
	private final static String QUEUE_NAME = "hello";

	public static void main(String[] args) throws IOException, TimeoutException {
		// 创建连接
		ConnectionFactory factory = new ConnectionFactory();
		// 设置 RabbitMQ 的主机名
		factory.setHost("localhost");
		// 创建一个连接
		Connection connection = factory.newConnection();
		// 创建一个通道
		Channel channel = connection.createChannel();
		// 指定一个队列
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		// 公平转发(在处理并确认前一个消息之前，不要向工作线程发送新消息。相反，它将发送到下一个还不忙的工作线程。)
		int prefetchCount = 1;
		channel.basicQos(prefetchCount);
		// 发送消息
		for (int i = 10; i > 0; i--) {
			String message = "Liang:" + i;
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
			System.out.println(" [x] Sent '" + message + "'");
		}
		// 关闭频道和连接
		channel.close();
		connection.close();
	}
}
