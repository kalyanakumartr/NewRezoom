package org.hbs.core.bean.model.channel;

import java.sql.Timestamp;

import org.hbs.core.bean.model.IMessages;

public interface IChannelMessages extends IMessages
{
	public String getExpiryDate();

	public String getMessageId();

	public Timestamp getNextDeliveryDate();

	public String getScheduledDate();

	public void setExpiryDate(String expiryDate);

	public void setMessageId(String messageId);

	public void setNextDeliveryDate(Timestamp nextDeliveryDate);

	public void setScheduledDate(String scheduledDate);
}
