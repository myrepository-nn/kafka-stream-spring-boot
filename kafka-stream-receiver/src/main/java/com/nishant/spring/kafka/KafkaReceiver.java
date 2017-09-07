package com.nishant.spring.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

public class KafkaReceiver {

	@KafkaListener(id="test",topics="nishantoutput")
	public void listen(String output) {
		System.out.println("data received is >>>"+output);
	}
}
