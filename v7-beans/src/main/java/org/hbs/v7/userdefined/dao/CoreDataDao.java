package org.hbs.v7.userdefined.dao;

import org.hbs.v7.dao.ICoreDataDao;
import org.hbs.v7.userdefined.model.ResumeData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoreDataDao extends JpaRepository<ResumeData, String>, ICoreDataDao
{

}
