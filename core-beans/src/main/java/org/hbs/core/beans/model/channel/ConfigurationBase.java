package org.hbs.core.beans.model.channel;

import org.hbs.core.beans.model.IConfiguration;
import org.hbs.core.util.IConstProperty;

public abstract class ConfigurationBase implements IConfiguration, IConstProperty
{
	private static final long	serialVersionUID	= -2543562538890272304L;
	
	private String				baseFolderPath;
	private String				connectionId;
	private String				producerId;

	public ConfigurationBase()
	{
		super();
	}

	public String getBaseFolderPath()
	{
		return baseFolderPath;
	}

	public String getConnectionId()
	{
		return connectionId;
	}

	public String getProducerId()
	{
		return producerId;
	}

	public void setBaseFolderPath(String baseFolderPath)
	{
		this.baseFolderPath = baseFolderPath;
	}

	public void setConnectionId(String connectionId)
	{
		this.connectionId = connectionId;
	}

	public void setProducerId(String producerId)
	{
		this.producerId = producerId;
	}

}