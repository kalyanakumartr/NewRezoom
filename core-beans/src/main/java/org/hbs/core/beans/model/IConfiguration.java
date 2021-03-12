package org.hbs.core.beans.model;

import org.hbs.core.util.IConstProperty;

public interface IConfiguration extends IConstProperty
{
	public String getProducerId();

	public void setProducerId(String producerId);

	public String getConnectionId();

	public void setConnectionId(String connectionId);

	public String getBaseFolderPath();

}