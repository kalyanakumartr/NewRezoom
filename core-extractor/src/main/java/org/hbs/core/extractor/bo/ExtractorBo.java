package org.hbs.core.extractor.bo;

import java.io.Serializable;
import java.util.List;

import org.hbs.core.bean.model.IConfiguration;
import org.hbs.core.bean.model.channel.DataExtractorPattern;
import org.hbs.core.security.resource.IPath.EMedia;
import org.hbs.core.security.resource.IPath.EMediaMode;

public interface ExtractorBo extends Serializable
{
	List<IConfiguration> getConfigurationList(EMedia eMedia, EMediaMode eMediaMode);

	long getLastEmailSentDate(String customerAccountMail);

	List<DataExtractorPattern> getEmailExtractors(String producerId);
}
