package org.hbs.v7.userdefined.dao;

import org.hbs.v7.dao.DynaRepo;
import org.hbs.v7.dao.ICoreBaseDao;
import org.hbs.v7.dao.ICoreDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DynaRepoImpl implements DynaRepo
{

	private static final long	serialVersionUID	= 6680897343117376145L;

	@Autowired
	CoreDataDao					coreDataDao;

	@Autowired
	CoreBaseDao					coreDao;

	public ICoreDataDao getCoreDataDao()
	{
		return coreDataDao;
	}

	public ICoreBaseDao getCoreDao()
	{
		return coreDao;
	}

}
