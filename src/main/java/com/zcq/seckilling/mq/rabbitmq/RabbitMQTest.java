package com.zcq.seckilling.mq.rabbitmq;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RabbitMQTest {
	@Autowired
	private Sender sender;
	@Autowired
	private Sender2 sender2;
	@Test
	public void send() throws Exception {
		sender.send();
		sender2.send();
	}

}
