package org.hbs.core.admin;

import java.io.Serializable;

import org.hbs.core.bean.model.IUsers;

public class UserParam implements Serializable
{

	private static final long	serialVersionUID	= -3356286913817568172L;
	public String				userId;
	public IUsers				user;
	public String				action;

	public UserParam()
	{
		super();
	}

	public UserParam(IUsers user)
	{
		this.user = user;
	}

}