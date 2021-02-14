package org.hbs.core.bean;

import org.hbs.core.security.resource.IPath.EMessageStatus;

public class DashboardStatusBean
{

	public DashboardStatusBean(String status)
	{
		this.status = status;
	}

	public DashboardStatusBean(EMessageStatus status)
	{
		this.status = status.name();
	}

	public String	status;
	public long		value;

}
