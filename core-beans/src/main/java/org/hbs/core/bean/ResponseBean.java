package org.hbs.core.bean;

public class ResponseBean extends APIStatus
{

	private static final long serialVersionUID = 8666015520688284014L;

	public static APIStatus createResponseBean(String messageCode)
	{
		ResponseBean response = new ResponseBean();
		return response.getStatusBeanWithMessage(messageCode);
	}

}
