package org.hbs.core.dao;

import org.hbs.core.bean.model.Roles;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesDao extends CrudRepository<Roles, String>
{

}
