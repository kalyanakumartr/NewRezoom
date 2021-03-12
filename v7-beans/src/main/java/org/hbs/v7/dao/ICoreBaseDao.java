package org.hbs.v7.dao;

import java.io.Serializable;
import java.util.Optional;

import org.hbs.v7.beans.model.ICoreBase;

public interface ICoreBaseDao extends Serializable
{
	Optional<?> findById(String dataURN);
	
	void save(ICoreBase coreBase);
}
