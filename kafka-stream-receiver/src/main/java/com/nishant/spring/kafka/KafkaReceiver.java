package com.nishant.spring.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

public class KafkaReceiver {

	@KafkaListener(id="test",topicPattern="nishantoutput")
	public void listen(@Payload String message,
			@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) Integer key,
			@Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
			@Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition
			) {
		System.out.println("received message..."+message+"<<key>>"+key+"<<topic>>"+topic+"<<partition>>"+partition);
	}
}
