package com.zcq.seckilling.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class EmitLogDirect {
	private static final String EXCHANGE_NAME = "direct_logs";
	private static final String[] LOG_LEVEL_ARR = {"debug", "info", "error"};

	public static void main(String[] args) throws IOException, TimeoutException {
		// 创建连接
		ConnectionFactory factory = new ConnectionFactory();
		// 设置 RabbitMQ 的主机名
		factory.setHost("localhost");
		// 创建一个连接
		Connection connection = factory.newConnection();
		// 创建一个通道
		Channel channel = connection.createChannel();
		// 指定一个交换器
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		// 发送消息
		for (int i = 0; i < 10; i++) {
			int rand = new Random().nextInt(3);
			String severity = LOG_LEVEL_ARR[rand];
			String message = "Liang-MSG log : [" + severity + "]" + UUID.randomUUID().toString();
			// 发布消息至交换器
			// 参数1 exchange ：交换器
			// 参数2 routingKey ： 路由键
			// 参数3 props ： 消息的其他参数
			// 参数4 body ： 消息体
			channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes());
			System.out.println(" [x] Sent '" + message + "'");
		}
		// 关闭频道和连接
		channel.close();
		connection.close();
	}
}
