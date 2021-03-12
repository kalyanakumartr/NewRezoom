package org.hbs.core.kafka;

import java.io.Serializable;

public interface IKafkaConstants extends Serializable
{
	public String	INTERNAL_TOPIC		= "InternalTopic";
	

	public String	EMAIL				= "Email";
	public String	SMS					= "SMS";

	public String	INTERNAL_GROUP		= "InternalGroup";
	
	public enum ETopic implements IETopic
	{
		Internal(INTERNAL_TOPIC);

		String topic;

		ETopic(String topic)
		{
			this.topic = topic;
		}

		public String getTopic()
		{
			return this.topic;
		}
	}
}
