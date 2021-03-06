package org.hbs.core.kafka;

import org.hbs.core.security.resource.IPath.ETemplate;
import org.hbs.core.util.EnumInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GenericKafkaProducer implements IKafkaConstants
{
	private static final long				serialVersionUID	= 1643040639961382216L;
	private static final Logger				LOGGER				= LoggerFactory.getLogger(GenericKafkaProducer.class);

	@Autowired
	private KafkaTemplate<String, Object>	kafkaTemplate;

	public void send(IETopic eTopic, IKAFKAPartition partition, Object object) throws JsonProcessingException
	{
		send(eTopic, partition, ETemplate.Default, object);
	}

	public void send(IETopic eTopic, IKAFKAPartition partition, EnumInterface eTemplate, Object object) throws JsonProcessingException
	{
		try
		{
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writeValueAsString(object);

			this.kafkaTemplate.send(eTopic.getTopic(), partition.getPartition(), eTemplate.name(), jsonString);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		LOGGER.info("Send Message ::: " + eTemplate.name());
	}
}
