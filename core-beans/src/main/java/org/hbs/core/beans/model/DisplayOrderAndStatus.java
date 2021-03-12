package org.hbs.core.beans.model;

import java.io.Serializable;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DisplayOrderAndStatus implements Serializable
{

	private static final long serialVersionUID = 1332882015033821125L;
	protected int displayOrder;
	protected boolean status;

	public DisplayOrderAndStatus()
	{
		super();
	}

	@Column(name = "displayOrder")
	@JsonIgnore
	public int getDisplayOrder()
	{
		return displayOrder;
	}

	@Column(name = "status")
	@JsonIgnore
	public boolean isStatus()
	{
		return status;
	}

	public void setDisplayOrder(int displayOrder)
	{
		this.displayOrder = displayOrder;
	}

	public void setStatus(boolean status)
	{
		this.status = status;
	}

}