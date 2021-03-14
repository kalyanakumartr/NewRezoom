package org.hbs.core.beans;

import org.hbs.core.beans.model.IConfiguration;
import org.hbs.core.beans.model.ProducersProperty;
import org.springframework.security.core.Authentication;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ConfigurationFormBean extends APIStatus
{

	private static final long	serialVersionUID	= 2490152380120347818L;

	public IConfiguration		configuration;

	public String				to;

	public String				messageCode;

	public ProducersProperty	producerProperty;

	public ProducersProperty	repoProducerProperty;

	public String				groupName;

	public String				autoId;
	
	public String				searchParam;

	public ConfigurationFormBean()
	{
		super();
	}

	public ConfigurationFormBean(IConfiguration configuration)
	{
		super();
		this.configuration = configuration;
	}

	public void updateRepoConfiguration(Authentication auth)
	{
		this.repoProducerProperty.modifiedUserInfo(auth);
		this.repoProducerProperty.setComments(this.producerProperty.getComments());
		this.repoProducerProperty.setEnumKey(this.producerProperty.getEnumKey());
		this.repoProducerProperty.setGroupName(this.producerProperty.getGroupName());
		this.repoProducerProperty.setMedia(this.producerProperty.getMedia());
		this.repoProducerProperty.setMediaMode(this.producerProperty.getMediaMode());
		this.repoProducerProperty.setMediaType(this.producerProperty.getMediaType());
		this.repoProducerProperty.setProperty(this.producerProperty.getProperty());
		this.repoProducerProperty.setValue(this.producerProperty.getValue());
	}

	@Override
	public void clearForm()
	{
		this.autoId = null;
		this.searchParam = null;
		this.groupName = null;
		this.to = null;
		this.configuration = null;
		this.producerProperty = null;
		this.repoProducerProperty = null;

	}

}
