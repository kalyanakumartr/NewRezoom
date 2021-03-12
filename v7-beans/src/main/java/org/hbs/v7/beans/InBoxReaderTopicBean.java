package org.hbs.v7.beans;

import java.io.Serializable;
import java.util.Date;

import org.hbs.v7.channel.AutoConfigurationEmail;
import org.springframework.messaging.MessagingException;

public class InBoxReaderTopicBean implements Serializable
{

	private static final long		serialVersionUID	= -6990780389417212191L;
	public int						messageNumber;
	public Date						sentDate;
	public AutoConfigurationEmail	config;

	public InBoxReaderTopicBean()
	{

	}

	public InBoxReaderTopicBean(int messageNumber, Date sentDate, AutoConfigurationEmail config) throws MessagingException
	{
		super();
		this.messageNumber = messageNumber;
		this.sentDate = sentDate;
		this.config = config;
	}

}
