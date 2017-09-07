package com.nishant.spring.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/kafka")
public class SenderController {
	@Autowired
	private KafkaSender kafkaSender;
	@Autowired
	private ObjectMapper objectMapper;
	@RequestMapping(value="/send/{topic}",method=RequestMethod.POST)
	public KafkaVO send(@RequestBody KafkaVO kafkaVO,@PathVariable String topic) throws JsonProcessingException {
		kafkaSender.send(topic,kafkaVO.getKey(), objectMapper.writeValueAsString(kafkaVO));
		return kafkaVO;
	}

}
