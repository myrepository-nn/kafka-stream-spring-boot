package com.nishant.spring.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
@Configuration
@EnableKafka
public class ReceiverConfig {
	@Bean
	public Map<String,Object> config(){
		Map<String,Object> conf=new HashMap<>();
		conf.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		conf.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
		conf.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		conf.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		conf.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
		return conf;
	}
	@Bean
	public ConsumerFactory<Integer, String> consumerFactory(){
		return new DefaultKafkaConsumerFactory<>(config());
	}
	@Bean
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, String>> kafkaListenerContainerFactory(){
		ConcurrentKafkaListenerContainerFactory<Integer, String> ckl=new ConcurrentKafkaListenerContainerFactory<>();
		ckl.setConcurrency(3);
		ckl.getContainerProperties().setPollTimeout(3000);
		ckl.setConsumerFactory(consumerFactory());
		return ckl;
	}
	@Bean
	public KafkaReceiver kafkaReceiver() {
		return new KafkaReceiver();
	}
}
