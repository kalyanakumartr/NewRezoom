package org.hbs.core.beans;

import org.hbs.core.beans.model.MessagesBase;

public class MessageDataFormBean extends APIStatus
{

	private static final long	serialVersionUID	= 1452795795821759203L;

	public MessagesBase			message;
	public String				searchParam;
	public String				userId;
	public String				groupId;
	public String				customerId;
	public long					fromDate;
	public long					toDate;
	public int					year;
	
	@Override
	public void clearForm() {
		// TODO Auto-generated method stub
		
	}

}
