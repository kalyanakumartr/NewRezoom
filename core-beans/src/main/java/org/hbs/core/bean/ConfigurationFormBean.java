package org.hbs.core.bean;

import org.hbs.core.bean.model.IConfiguration;
import org.hbs.core.bean.model.ProducersProperty;
import org.springframework.security.core.Authentication;

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

	}

}
