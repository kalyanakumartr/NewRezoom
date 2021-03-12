package org.hbs.v7.userdefined.model;

import java.sql.Timestamp;

import org.hbs.v7.beans.model.ICoreBase;

public class CoreBaseFactory
{
	public static ICoreBase getInstance(String... identifier)
	{
		Resume resume = new Resume();
		resume.getProducerList().add(0, new CustomerProducer(identifier[0]));
		resume.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		resume.setStatus(true);
		return resume;
	}
}
