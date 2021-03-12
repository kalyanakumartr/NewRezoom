package org.hbs.v7.beans.model;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import org.hbs.core.beans.model.CommonDateAndStatusFields;
import org.hbs.core.util.EBusinessKey;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@MappedSuperclass
public abstract class ACoreBase extends CommonDateAndStatusFields implements ICoreBase, EBusinessKey
{
	private static final long		serialVersionUID	= 5357766946700135577L;
	protected String				dataURN;
	protected Set<DataAttachments>	attachmentList		= new LinkedHashSet<DataAttachments>(0);

	public ACoreBase()
	{
		super();
	}

	@Override
	@Id
	@Column(name = "dataURN")
	public String getDataURN()
	{
		return dataURN;
	}

	@Override
	@OneToMany(targetEntity = DataAttachments.class, fetch = FetchType.LAZY, mappedBy = "dataURN", cascade = CascadeType.ALL)
	@Fetch(FetchMode.JOIN)
	public Set<DataAttachments> getAttachmentList()
	{
		return attachmentList;
	}

	@Override
	public void setDataURN(String dataURN)
	{
		this.dataURN = dataURN;
	}

	@Override
	public void setAttachmentList(Set<DataAttachments> attachmentList)
	{
		this.attachmentList = attachmentList;
	}
}