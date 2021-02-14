package org.hbs.sender;

import org.hbs.core.bean.MessageFormBean;
import org.hbs.core.bean.SMSResponseBean;
import org.hbs.core.security.resource.IPath;

public interface ISMSGateway extends IPath
{
	SMSResponseBean sendSMSMessages(MessageFormBean messageFormBean);

	String callBackResponse(String responseUrl, String token, String messageId);
}
