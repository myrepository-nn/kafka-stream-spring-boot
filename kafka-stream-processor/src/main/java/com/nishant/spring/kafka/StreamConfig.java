package com.nishant.spring.kafka;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.Reducer;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.core.KStreamBuilderFactoryBean;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableKafka
@EnableKafkaStreams
public class StreamConfig { 

	@Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
	public StreamsConfig kStreamsConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "testStreams");
		props.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.Integer().getClass().getName());
		props.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
		props.put(StreamsConfig.TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor.class.getName());
		return new StreamsConfig(props);
	}
	@Bean(name = "defaultKStreamBuilder")
	public FactoryBean<KStreamBuilder> myKStreamBuilder(StreamsConfig streamsConfig) {
		return new KStreamBuilderFactoryBean(streamsConfig);
	}
	@Bean
	public KStream<Integer, String> kStream(KStreamBuilder kStreamBuilder) {
		KStream<Integer, String> stream = kStreamBuilder.stream("nishant");
		stream.mapValues(String -> ObjectUpdateMethod(String)).
		to("nishantoutput");
		stream.print();

		return stream;
	}
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	private String ObjectUpdateMethod(String arg0) {
		String result="";
		try {
			KafkaVO kafkaVO=objectMapper().readValue(arg0, KafkaVO.class);
			KafkaVO kafkaVOUpdate=new KafkaVO();
			kafkaVOUpdate.setKey(kafkaVO.getKey());
			kafkaVOUpdate.setName(kafkaVO.getName()+"..Updated after stream processing");
			kafkaVOUpdate.setDescription(kafkaVO.getDescription()+"..Updated after stream processing");
			kafkaVOUpdate.setVersion(kafkaVO.getVersion()+"..Updated after stream processing");
			result=objectMapper().writeValueAsString(kafkaVOUpdate);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
