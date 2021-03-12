package org.hbs.v7.dao;

import java.io.Serializable;

public interface DynaRepo extends Serializable
{
	public ICoreDataDao getCoreDataDao();

	public ICoreBaseDao getCoreDao();
}
