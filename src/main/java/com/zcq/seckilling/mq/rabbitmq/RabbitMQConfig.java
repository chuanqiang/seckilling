package com.zcq.seckilling.mq.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitMQConfig {
	public static final String QUEUE_NAME = "spring-boot-simple";
	@Bean
	public Queue queue() {
		return new Queue(QUEUE_NAME);
	}
}