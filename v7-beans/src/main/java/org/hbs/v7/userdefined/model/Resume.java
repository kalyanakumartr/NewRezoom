package org.hbs.v7.userdefined.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hbs.v7.beans.model.ACoreBase;
import org.hbs.v7.beans.model.ICoreData;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "resume")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Resume extends ACoreBase
{
	private static final long			serialVersionUID	= 5049651757005693167L;

	protected List<CustomerProducer>	producerList		= new ArrayList<CustomerProducer>();
	protected ICoreData					coreData;

	public Resume()
	{
		super();
		this.dataURN = getBusinessKey();
	}

	public Resume(String dataURN)
	{
		super();
		this.dataURN = dataURN;
	}

	// Mapped To Multiple Producers, which means multiple branches
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "resume_producers", joinColumns = @JoinColumn(name = "dataURN"), inverseJoinColumns = @JoinColumn(name = "producerId"))
	public List<CustomerProducer> getProducerList()
	{
		return producerList;
	}

	@ManyToOne(targetEntity = ResumeData.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "dataURN", insertable = false, updatable = false)
	@JsonIgnore
	public ICoreData getCoreData()
	{
		return coreData;
	}

	public void setProducerList(List<CustomerProducer> producerList)
	{
		this.producerList = producerList;
	}

	public void setCoreData(ICoreData coreData)
	{
		this.coreData = coreData;
	}

	@Override
	public String getBusinessKey(String... combination)
	{
		return EKey.Auto();
	}

	@Override
	public String constructCountryTimeZone()
	{
		return null;
	}
}
