package com.zcq.seckilling.mq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Recv {
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
		// queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
		// 参数1 queue ：队列名
		// 参数2 durable ：是否持久化:当 RabbitMQ 退出或崩溃时，将会丢失所有的队列和信息,因此需要做持久化来防止这种情况
		// 参数3 exclusive ：仅创建者可以使用的私有队列，断开后自动删除
		// 参数4 autoDelete : 当所有消费客户端连接断开后，是否自动删除队列
		// 参数5 arguments
		boolean durable = true;
		channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		// 创建队列消费者
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + message + "'");
				try {
					doWork(message);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		};
		boolean autoAck = true;
		// basicConsume(String queue, boolean autoAck, Consumer callback)
		// 参数1 queue ：队列名
		// 参数2 autoAck ： 是否自动ACK（acknowledgement character 命令正确应答）
		//      从消费者发送一个确认信息告诉 RabbitMQ 已经收到消息并已经被接收和处理，然后RabbitMQ 才可以自由删除它；否则分配给其他消费者。默认开启状态。
		// 参数3 callback ： 消费者对象的一个接口，用来配置回调
		channel.basicConsume(QUEUE_NAME, autoAck, consumer);
	}
	private static void doWork(String task) throws InterruptedException {
		String[] taskArr = task.split(":");
		TimeUnit.SECONDS.sleep(Long.valueOf(taskArr[1]));
	}
}
