package org.hbs.core.extractor.bo;

import java.io.Serializable;
import java.util.List;

import org.hbs.core.beans.model.IConfiguration;
import org.hbs.core.beans.model.channel.DataExtractorPattern;
import org.hbs.core.security.resource.IPathBase.EMedia;
import org.hbs.core.security.resource.IPathBase.EMediaMode;

public interface ExtractorBo extends Serializable
{
	List<IConfiguration> getConfigurationList(EMedia eMedia, EMediaMode eMediaMode);

	long getLastEmailSentDate(String customerAccountMail);

	List<DataExtractorPattern> getEmailExtractors(String producerId);
}
