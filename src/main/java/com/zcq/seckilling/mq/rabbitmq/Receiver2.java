package com.zcq.seckilling.mq.rabbitmq;

import org.springframework.stereotype.Service;

@Service
public class Receiver2 {

	public void receiveMessage(String message) {
		System.out.println("Received <" + message + ">");
	}
}
