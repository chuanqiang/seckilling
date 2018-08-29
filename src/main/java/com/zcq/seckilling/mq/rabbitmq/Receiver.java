package com.zcq.seckilling.mq.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Receiver {
	@Autowired
	private AmqpTemplate rabbitTemplate;
	@RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
	public void receiveMessage(String message) {
		System.out.println("Received <" + message + ">");
	}
}
