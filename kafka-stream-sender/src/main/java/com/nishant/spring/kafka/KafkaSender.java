package com.nishant.spring.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

public class KafkaSender {
	@Autowired
	private KafkaTemplate<Integer,String> kafkaTemplate;
	public void send(String topic,Integer key,String message) {
		Message<String> msg=MessageBuilder.withPayload(message).setHeader(KafkaHeaders.MESSAGE_KEY, key).setHeader(KafkaHeaders.TOPIC, topic).build();
		 ListenableFuture<SendResult<Integer, String>>  lstfut=kafkaTemplate.send(msg);
		 lstfut.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {

			@Override
			public void onSuccess(SendResult<Integer, String> arg0) {
				// add logger here
				System.out.println("success callback"+arg0.getRecordMetadata().topic());
				
			}

			@Override
			public void onFailure(Throwable ex) {
				// add logger here
				ex.printStackTrace();
				
			}
		});
	}

}
