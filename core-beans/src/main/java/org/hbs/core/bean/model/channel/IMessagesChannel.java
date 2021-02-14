package org.hbs.core.bean.model.channel;

import java.sql.Timestamp;

import org.hbs.core.bean.model.ICommonDateAndStatusFields;
import org.hbs.core.bean.model.IMessages;
import org.hbs.core.bean.model.IProducersBase;
import org.hbs.core.util.ICRUDBean;

public interface IMessagesChannel extends ICommonDateAndStatusFields, IProducersBase, ICRUDBean, IMessages
{
	public String getExpiryDate();

	public Timestamp getNextDeliveryDate();

	public String getScheduledDate();

	public void setExpiryDate(String expiryDate);

	public void setNextDeliveryDate(Timestamp nextDeliveryDate);

}