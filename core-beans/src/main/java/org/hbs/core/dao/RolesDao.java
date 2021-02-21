package org.hbs.core.dao;

import org.hbs.core.beans.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesDao extends JpaRepository<Roles, String>
{

}
