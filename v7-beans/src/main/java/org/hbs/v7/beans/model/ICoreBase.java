package org.hbs.v7.beans.model;

import java.util.List;
import java.util.Set;

import org.hbs.core.beans.model.ICommonDateAndStatusFields;
import org.hbs.core.beans.model.IProducers;

public interface ICoreBase extends ICommonDateAndStatusFields
{
	public String getDataURN();

	public Set<DataAttachments> getAttachmentList();

	public void setDataURN(String dataURN);

	public void setAttachmentList(Set<DataAttachments> attachmentList);

	public List<? extends IProducers> getProducerList();
}